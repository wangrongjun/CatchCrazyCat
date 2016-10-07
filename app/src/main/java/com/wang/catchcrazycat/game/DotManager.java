package com.wang.catchcrazycat.game;

import com.wang.java_util.MathUtil;
import com.wang.java_util.PairList;

import java.util.ArrayList;
import java.util.List;

/**
 * by 王荣俊 on 2016/10/6.
 */
public class DotManager {

    private int rowNumber;//行数
    private int columnNumber;//列数
    private int blockNumber;//初始化时随机路障的数量

    private Dot[][] dots;

    private Dot cat;//dots的猫所在点的一个引用对象，仅仅为了避免每次寻找猫都做二重循环，没有也没关系。

    public DotManager(int rowNumber, int columnNumber, int blockNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.blockNumber = blockNumber;
        dots = new Dot[rowNumber][columnNumber];

//        新建空白点和中间的猫点
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                dots[i][j] = new Dot(j, i);//点的坐标xy与数组的下标ij行列相反
                if (i == rowNumber / 2 && j == columnNumber / 2) {
                    dots[i][j].setState(Dot.State_Cat);//选择中间点为猫的所在点
                    cat = dots[i][j];
                }
            }
        }

//        随机选择blockNumber个空白点成为路障点
        int i = blockNumber;
        while (i > 0) {
            int random = MathUtil.random(0, rowNumber * columnNumber - 1);
            Dot dot = dots[random / rowNumber][random % columnNumber];
            if (dot.getState() == Dot.State_Null) {
                dot.setState(Dot.State_Block);
                i--;
            }
        }

    }

    public static final int STATE_CONTINUE = 1;
    public static final int STATE_WIN = 2;
    public static final int STATE_LOSS = 3;

    /**
     * 判断情况并移动猫到下一步，这是整个游戏的核心逻辑算法
     *
     * @return 返回猫移动后的游戏状态
     */
    public int calculateNextAndMoveCat() {

//        若猫已到边界
        if (atEdge(cat)) {
            return STATE_LOSS;
        }

//        遍历cat的全部邻居，找到空的邻居点并存进数组
        List<Integer> availableDirectoryList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Dot neighbourhood = getNeighbourhood(cat, i);
            assert neighbourhood != null;//因为cat不是边界点，所有一定不为空
            if (neighbourhood.getState() == Dot.State_Null) {
                availableDirectoryList.add(i);
            }
        }

//        若没有空的邻居点，说明已被包围，赢了
        if (availableDirectoryList.size() == 0) {
            return STATE_WIN;
        }

//        只有一个方向可以走
        if (availableDirectoryList.size() == 1) {
            moveCat(availableDirectoryList.get(0));
//            Util.toast("只有一个方向可以走");
            return STATE_CONTINUE;
        }

//        若有多个方向可走，找出最优的方向，优先规则如下：
//        1.若存在一个到多个没有路障可以一直走到边界的方向，走距离边界最短的方向。
//        2.若不存在，走距离路障点距离最远的方向
        PairList<Integer, Integer> directoryDistanceList = new PairList<>();
        for (int i = 0; i < availableDirectoryList.size(); i++) {
            int directory = availableDirectoryList.get(i);
            directoryDistanceList.add(directory, getDistance(cat, directory));
        }

//        选取正数的最小，若没有，则选取负数的最小（不会出现0的情况，因为这几个方向都是可移动的）
        int minPositiveDistance = 999;
        int minPositiveDirectory = 0;
        int minNegativeDistance = 0;
        int minNegativeDirectory = 0;
        for (int i = 0; i < directoryDistanceList.size(); i++) {
            int distance = directoryDistanceList.getRight(i);
            if (distance > 0) {
                if (minPositiveDistance > distance) {
                    minPositiveDistance = distance;
                    minPositiveDirectory = directoryDistanceList.getLeft(i);
                } else if (minPositiveDistance == distance) {//如果相等，说明存在两条以上距离相同的全通方向，0到80%的几率选择是否使用最新的方向。
                    int random = MathUtil.random(0, 80);
                    if (MathUtil.random(0, 100) < random) {
                        minPositiveDirectory = directoryDistanceList.getLeft(i);
                    }
                }
            } else {//distance<0
                if (minNegativeDistance > distance) {
                    minNegativeDistance = distance;
                    minNegativeDirectory = directoryDistanceList.getLeft(i);
                } else if (minNegativeDistance == distance) {//如果相等，说明存在两条以上距离相同的有路障方向，0到80%的几率选择是否使用最新的方向。
                    int random = MathUtil.random(0, 80);
                    if (MathUtil.random(0, 100) < random) {
                        minNegativeDirectory = directoryDistanceList.getLeft(i);
                    }
                }
            }
        }
        if (minPositiveDistance < 999) {//存在没有路障的方向
            moveCat(minPositiveDirectory);
//            Util.toast("没有路障的方向，到边界距离：" + minPositiveDistance);
        } else {
            moveCat(minNegativeDirectory);
//            Util.toast("有路障的方向，到路障距离：" + minNegativeDistance);
        }

        return STATE_CONTINUE;

    }

    /**
     * 获取某一点沿指定方向到边界或路障的距离，是游戏的逻辑算法之一
     * <p/>
     * 注意：该方法有一点问题，就是如果路障为边界点，会返回正数（即忽略了该路障）。
     * 经实践证明忽略了边界路障后会更智能，更难玩，所以决定保留该问题。
     *
     * @return 指定方向到边界没有路障，返回正数。有路障，返回负数。
     */
    private int getDistance(Dot dot, int directory) {

        if (atEdge(dot)) {
            return 0;
        }

        int distance = 0;
        Dot neighbourhood = dot;
        while (!atEdge(neighbourhood)) {
            distance++;
            neighbourhood = getNeighbourhood(neighbourhood, directory);
            if (neighbourhood.getState() == Dot.State_Block) {
                distance = -distance;
                return distance;
            }
        }
        return distance;
    }

    /**
     * 判断某一点是否为边界点，是游戏的逻辑算法之一
     */
    private boolean atEdge(Dot dot) {
        return dot.getX() == 0 ||
                dot.getY() == 0 ||
                dot.getX() == columnNumber - 1 ||
                dot.getY() == rowNumber - 1;
    }

    /**
     * 获取某一点的指定位置的邻居点，是游戏的逻辑算法之一
     * <p/>
     * 注意：该方法不作越界检查，dot为边界点时可能出错（数组越界）
     *
     * @param directory 0,1,2,3,4,5（左，左上，右上，右，右下，左下）
     */
    private Dot getNeighbourhood(Dot dot, int directory) {
        if (dot.getY() % 2 == 1) {//偶数行（从1数起）
            switch (directory) {
                case 0:
                    return getDot(dot.getX() - 1, dot.getY());
                case 1:
                    return getDot(dot.getX(), dot.getY() - 1);
                case 2:
                    return getDot(dot.getX() + 1, dot.getY() - 1);
                case 3:
                    return getDot(dot.getX() + 1, dot.getY());
                case 4:
                    return getDot(dot.getX() + 1, dot.getY() + 1);
                case 5:
                    return getDot(dot.getX(), dot.getY() + 1);
            }
        } else {//奇数行（从1数起）
            switch (directory) {
                case 0:
                    return getDot(dot.getX() - 1, dot.getY());
                case 1:
                    return getDot(dot.getX() - 1, dot.getY() - 1);
                case 2:
                    return getDot(dot.getX(), dot.getY() - 1);
                case 3:
                    return getDot(dot.getX() + 1, dot.getY());
                case 4:
                    return getDot(dot.getX(), dot.getY() + 1);
                case 5:
                    return getDot(dot.getX() - 1, dot.getY() + 1);
            }
        }
        return null;
    }

    private void moveCat(int directory) {
        getDot(cat.getX(), cat.getY()).setState(Dot.State_Null);
        cat = getNeighbourhood(cat, directory);
        assert cat != null;
        cat.setState(Dot.State_Cat);
    }

    public Dot getDot(int x, int y) {
        if (x < columnNumber && y < rowNumber) {
            return dots[y][x];
        } else {
            return null;
        }
    }

    public void setBlock(int x, int y) {
        dots[y][x].setState(Dot.State_Block);
    }

    public interface INext {
        /**
         * @return 若返回true，继续遍历，否则结束。
         */
        boolean next(float x, float y, int dotState);
    }

    public void iterator(INext iNext) {
        for (int y = 0; y < rowNumber; y++) {
            for (int x = 0; x < columnNumber; x++) {
                if (y % 2 == 1) {//若为偶数行（从1数起），则右移半个点的距离
                    boolean next = iNext.next(x + 0.5f, y, getDot(x, y).getState());
                    if (!next) {
                        return;
                    }
                } else {
                    boolean next = iNext.next(x, y, getDot(x, y).getState());
                    if (!next) {
                        return;
                    }
                }
            }
        }
    }

}

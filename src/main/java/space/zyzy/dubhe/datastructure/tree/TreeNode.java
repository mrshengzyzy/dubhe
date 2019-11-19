package space.zyzy.dubhe.datastructure.tree;

/**
 * Tree入门
 */
public class TreeNode {

    // 左节点(儿子)
    private TreeNode lefTreeNode;

    // 右节点(儿子)
    private TreeNode rightNode;

    // 数据
    private int value;

    public TreeNode getLefTreeNode() {
        return lefTreeNode;
    }

    public void setLefTreeNode(TreeNode lefTreeNode) {
        this.lefTreeNode = lefTreeNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TreeNode{");
        sb.append("lefTreeNode=").append(lefTreeNode);
        sb.append(", rightNode=").append(rightNode);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 静态方式构建树
     */
    private static TreeNode staticCreate() {

        TreeNode root3 = new TreeNode(3);

        TreeNode l7 = new TreeNode(7);
        TreeNode ll2 = new TreeNode(2);
        TreeNode lr6 = new TreeNode(6);
        TreeNode lrl5 = new TreeNode(5);
        TreeNode lrr8 = new TreeNode(8);

        TreeNode r1 = new TreeNode(1);
        TreeNode rr9 = new TreeNode(9);
        TreeNode rrl4 = new TreeNode(4);

        // 节点6
        lr6.setLefTreeNode(lrl5);
        lr6.setRightNode(lrr8);

        // 节点7
        l7.setLefTreeNode(ll2);
        l7.setRightNode(lr6);

        // 根节点3
        root3.setLefTreeNode(l7);
        root3.setRightNode(r1);

        // 节点1
        r1.setRightNode(rr9);

        // 节点9
        rr9.setLefTreeNode(rrl4);

        return root3;
    }

    /**
     * 构建一棵二叉查找树
     *
     * @param root  根节点
     * @param value 将要插入的值
     */
    private static void dynamicCreate(TreeNode root, int value) {

        // 如果当前节点为null,表示父级节点没有子节点
        if (root == null) {
            return;
        }

        // 根节点的值
        int rootValue = root.getValue();

        // 新的节点
        TreeNode node = new TreeNode(value);

        // 新的值小，应该放在左变
        if (value < rootValue) {

            // 左子树
            TreeNode leftNode = root.getLefTreeNode();

            // 没有左子树,直接插入新值
            if (leftNode == null) {
                root.setLefTreeNode(node);
                return;
            }

            // 否则以左子树为根插入
            dynamicCreate(leftNode, value);
            return;
        }

        // 否则放在右边
        TreeNode rightNode = root.getRightNode();

        // 右子树位null,直接插入新树
        if (rightNode == null) {
            root.setRightNode(node);
            return;
        }

        // 否则以右子树为根插入
        dynamicCreate(rightNode, value);
    }

    // ====================================================================================================== //

    /**
     * 中序遍历
     * 先访问根节点,然后访问左节点,最后访问右节点
     */
    private static void inTraverseTree(TreeNode rootTreeNode) {

        // 如果当前节点为null,表示父级节点没有子节点
        if (rootTreeNode != null) {

            // 访问根节点
            System.out.print(rootTreeNode.getValue() + "->");

            // 访问左节点
            inTraverseTree(rootTreeNode.getLefTreeNode());

            // 访问右节点
            inTraverseTree(rootTreeNode.getRightNode());
        }
    }

    /**
     * 先序遍历
     * 先访问左节点,然后访问根节点,最后访问右节点
     */
    private static void preTraverseTree(TreeNode rootTreeNode) {

        // 如果当前节点为null,表示父级节点没有子节点
        if (rootTreeNode != null) {

            // 访问左节点
            preTraverseTree(rootTreeNode.getLefTreeNode());

            // 访问根节点
            System.out.print(rootTreeNode.getValue() + "->");

            // 访问右节点
            preTraverseTree(rootTreeNode.getRightNode());
        }
    }

    /**
     * 后序遍历
     * 先访问左节点,然后访问右节点,最后访问根节点
     */
    private static void postTraverseTree(TreeNode rootTreeNode) {

        // 如果当前节点为null,表示父级节点没有子节点
        if (rootTreeNode != null) {

            // 访问左节点
            postTraverseTree(rootTreeNode.getLefTreeNode());

            // 访问右节点
            postTraverseTree(rootTreeNode.getRightNode());

            // 访问根节点
            System.out.print(rootTreeNode.getValue() + "->");
        }
    }

    // ====================================================================================================== //


    public static void main(String[] args) {

        // 静态创建树
        TreeNode root = staticCreate();

        // 遍历
        inTraverseTree(root);
        System.out.println();
        preTraverseTree(root);
        System.out.println();
        postTraverseTree(root);
    }
}

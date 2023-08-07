package co.edu.uptc.logica;

public class TreeNode <T>{
    private T info;

    private TreeNode<T> left;
    private TreeNode<T> rigth;

    public TreeNode(T info){
        this.info = info;
        left = rigth=null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRigth() {
        return rigth;
    }

    public void setRigth(TreeNode<T> rigth) {
        this.rigth = rigth;
    }
}

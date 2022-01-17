package pascal.subset.interpreter.ast;

import pascal.subset.interpreter.tokens.AssignToken;

public class AssignNode implements Node {
   final Node left;
   final AssignToken operation;
   final Node right;

   public AssignNode(final Node left, final AssignToken operation, final Node right) {
      this.left = left;
      this.operation = operation;
      this.right = right;
   }

   public Node left() {
      return left;
   }

   public AssignToken operation() {
      return operation;
   }

   public Node right() {
      return right;
   }

   @Override
   public String toString() {
      return "AssignNode{" +
              "left=" + left +
              ", operation=" + operation +
              ", right=" + right +
              '}';
   }
}

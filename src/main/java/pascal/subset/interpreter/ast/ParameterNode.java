package pascal.subset.interpreter.ast;

public class ParameterNode implements Node {
   final String name;
   final VariableTypeNode type;

   public ParameterNode(final String name, final VariableTypeNode type) {
      this.name = name;
      this.type = type;
   }

   public String name() {
      return name;
   }

   public VariableTypeNode type() {
      return type;
   }

   @Override
   public String toString() {
      return "ParameterNode{" +
              "name='" + name + '\'' +
              ", type=" + type +
              '}';
   }
}

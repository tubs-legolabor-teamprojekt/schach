package js.tinyvm;

import java.io.IOException;

import js.tinyvm.io.IByteWriter;
import js.tinyvm.io.IOUtilities;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;

public class ConstantRecord implements WritableData
{
   Binary iBinary;

   /**
    * Constant.
    */
   Constant _constant;

   /**
    * Deferenced value.
    */
   ConstantValue _constantValue;
   
   boolean isUsed = false;

   /**
    * Constructor.
    * 
    * @param pool constant pool
    * @param constant constant
    * @param aBinary 
    */
   public ConstantRecord (ConstantPool pool, Constant constant, Binary aBinary)
   {
      iBinary = aBinary;
      _constantValue = new ConstantValue(pool, constant, iBinary);
   }

   public ConstantRecord (ClassRecord crec, Binary aBinary)
   {
       iBinary = aBinary;
       _constantValue = new ConstantValue(crec, aBinary);
   }
   /**
    * Get dereferenced value.
    */
   public ConstantValue constantValue ()
   {
      assert _constantValue != null: "Postconditon: result != null";
      return _constantValue;
   }

   /**
    * Equals based on equality of referenced value.
    */
   public boolean equals (Object object)
   {
      return object instanceof ConstantRecord
         && _constantValue.value().equals(
            ((ConstantRecord) object)._constantValue.value());
   }

   /**
    * hashCode based on referenced value.
    */
   public int hashCode ()
   {
      return _constantValue.value().hashCode();
   }

   /**
    * Get length of this record.
    */
   public int getLength ()
   {
      return IOUtilities.adjustedSize(2 + // offset
         1 + // type
         1, // size
         2);
   }

   /**
    * Dump.
    * 
    * @param writer byte writer
    */
   public void dump (IByteWriter writer) throws TinyVMException
   {
      assert writer != null: "Precondition: writer != null";

      try
      {
         writer.writeU2(_constantValue.getOffset());
         writer.writeU1(_constantValue.getType().type());
         writer.writeU1(_constantValue.getLength());
         IOUtilities.writePadding(writer, 2);
      }
      catch (IOException e)
      {
         throw new TinyVMException(e.getMessage(), e);
      }
   }
   
   public void markUsed()
   {
       isUsed = true;
       _constantValue.markUsed();
   }
   
   public boolean used()
   {
       return isUsed;
   }
}
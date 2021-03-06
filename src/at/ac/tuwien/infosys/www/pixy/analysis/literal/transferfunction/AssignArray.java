package at.ac.tuwien.infosys.www.pixy.analysis.literal.transferfunction;

import at.ac.tuwien.infosys.www.pixy.analysis.AbstractLatticeElement;
import at.ac.tuwien.infosys.www.pixy.analysis.AbstractTransferFunction;
import at.ac.tuwien.infosys.www.pixy.analysis.literal.LiteralLatticeElement;
import at.ac.tuwien.infosys.www.pixy.conversion.AbstractTacPlace;
import at.ac.tuwien.infosys.www.pixy.conversion.Variable;

/**
 * Transfer function for array assignment nodes ("left = array()").
 * @author Nenad Jovanovic <enji@seclab.tuwien.ac.at>
 */
public class AssignArray extends AbstractTransferFunction {
    private Variable left;
    private boolean supported;

// *********************************************************************************
// CONSTRUCTORS ********************************************************************
// *********************************************************************************

    public AssignArray(AbstractTacPlace left) {

        this.left = (Variable) left;    // must be a variable

        // note that we DO support such statements for arrays and array elements
        this.supported = !(this.left.isVariableVariable() || this.left.isMember());
    }

// *********************************************************************************
// OTHER ***************************************************************************
// *********************************************************************************

    public AbstractLatticeElement transfer(AbstractLatticeElement inX) {

        // if this statement is not supported by our alias analysis,
        // we simply ignore it
        if (!supported) {
            return inX;
        }

        LiteralLatticeElement in = (LiteralLatticeElement) inX;
        LiteralLatticeElement out = new LiteralLatticeElement(in);

        // let the lattice element handle the details (set the whole subtree
        // to NULL);
        // LATER: the actual result is more imprecise than it seems, because
        // "$x = array()" is translated to "_t0 = array(); $x = _t0", and
        // since there are no known array elements of _t0, the elements of
        // $x become TOP instead of NULL; for taint analysis, this issue
        // is handled by using array labels; could also be done here
        out.assignArray(left);

        return out;
    }
}
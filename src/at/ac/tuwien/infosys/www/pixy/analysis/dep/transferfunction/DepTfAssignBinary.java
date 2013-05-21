package at.ac.tuwien.infosys.www.pixy.analysis.dep.transferfunction;

import at.ac.tuwien.infosys.www.pixy.analysis.LatticeElement;
import at.ac.tuwien.infosys.www.pixy.analysis.TransferFunction;
import at.ac.tuwien.infosys.www.pixy.analysis.dep.DepLatticeElement;
import at.ac.tuwien.infosys.www.pixy.conversion.TacPlace;
import at.ac.tuwien.infosys.www.pixy.conversion.Variable;
import at.ac.tuwien.infosys.www.pixy.conversion.cfgnodes.CfgNode;

import java.util.Set;

/**
 * Transfer function for binary assignment nodes.
 *
 * @author Nenad Jovanovic <enji@seclab.tuwien.ac.at>
 */
public class DepTfAssignBinary extends TransferFunction {
    private Variable left;
    private TacPlace leftOperand;
    private TacPlace rightOperand;
    private int op;
    private Set<Variable> mustAliases;
    private Set<Variable> mayAliases;
    private CfgNode cfgNode;

// *********************************************************************************
// CONSTRUCTORS ********************************************************************
// *********************************************************************************

    // mustAliases, mayAliases: of setMe
    public DepTfAssignBinary(
        TacPlace left, TacPlace leftOperand, TacPlace rightOperand, int op, Set<Variable> mustAliases,
        Set<Variable> mayAliases, CfgNode cfgNode
    ) {
        this.left = (Variable) left;  // must be a variable
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.op = op;
        this.mustAliases = mustAliases;
        this.mayAliases = mayAliases;
        this.cfgNode = cfgNode;
    }

// *********************************************************************************
// OTHER ***************************************************************************
// *********************************************************************************

    public LatticeElement transfer(LatticeElement inX) {

        DepLatticeElement in = (DepLatticeElement) inX;
        DepLatticeElement out = new DepLatticeElement(in);

        // let the lattice element handle the details
        out.assign(left, mustAliases, mayAliases, cfgNode);

        return out;
    }
}
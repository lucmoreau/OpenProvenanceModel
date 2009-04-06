package org.openprovenance.model;
import java.util.List;
import java.util.LinkedList;

/** Utilities for manipulating OPM Graphs. */

public class OPMUtilities {

    private OPMFactory of=new OPMFactory();

    public List<Node> getNodes(OPMGraph g) {
        List<Node> res=new LinkedList();
        res.addAll(g.getArtifacts().getArtifact());
        res.addAll(g.getProcesses().getProcess());
        res.addAll(g.getAgents().getAgent());
        return res;
    }

    public List<Edge> getEdges(OPMGraph g) {
        List<Edge> res=new LinkedList();
        CausalDependencies dep=g.getCausalDependencies();
        for (Object o:dep.getUsedOrWasGeneratedByOrWasTriggeredBy()) {
            res.add((Edge)o);
        }
        return res;
    }

        

    public OPMGraph union (OPMGraph g1, OPMGraph g2) {

        Accounts accs=union(g1.getAccounts(),g2.getAccounts());

        Processes ps=union(g1.getProcesses(),g2.getProcesses());

        Artifacts as=union(g1.getArtifacts(),g2.getArtifacts());

        Agents ags=union(g1.getAgents(),g2.getAgents());

        CausalDependencies lks=union(g1.getCausalDependencies(),
                                     g2.getCausalDependencies());

        OPMGraph g=of.newOPMGraph(accs,
                                  ps,
                                  as,
                                  ags,
                                  lks);
        return g;
    }

    public Accounts union (Accounts g1, Accounts g2) {
        Accounts res=of.newAccounts(null,null);
        if (g1.getAccount()!=null) {
            res.getAccount().addAll(g1.getAccount());
        }
        if (g2.getAccount()!=null) {
            res.getAccount().addAll(g2.getAccount());
        }
        if (g1.getOverlaps()!=null) {
            res.getOverlaps().addAll(g1.getOverlaps());
        }
        if (g2.getOverlaps()!=null) {
            res.getOverlaps().addAll(g2.getOverlaps());
        }
        return res;
    }

    public Agents union (Agents g1, Agents g2) {
        return null;
    }

    public Processes union (Processes g1, Processes g2) {
        return null;
    }

    public Artifacts union (Artifacts g1, Artifacts g2) {
        return null;
    }

    public CausalDependencies union (CausalDependencies g1, CausalDependencies g2) {
        return null;
    }

    /** Returns a graph with the same structure, in which the *
     effective membership of all nodes has been computed.  The
     function returns an entirely new graph, without modifying the
     original. */
    
    public OPMGraph effectiveMembership (OPMGraph g) {
        Accounts accs=g.getAccounts();

        Processes ps=g.getProcesses();

        Artifacts as=g.getArtifacts();

        Agents ags=g.getAgents();

        CausalDependencies lks=g.getCausalDependencies();

        OPMGraph g2=of.newOPMGraph(accs,
                                   ps,
                                   as,
                                   ags,
                                   lks);
        return g2;
    }

    public Accounts accountMembership (ArtifactId aid, OPMGraph g) {
        return null;
    }

    public OPMGraph view (OPMGraph g, Accounts accs) {
        return g;
    }

    public boolean legalAccount (OPMGraph g) {
        return true;
    }

    public OPMGraph intersection (OPMGraph g1, OPMGraph g2) {
        return g1;
    }

}


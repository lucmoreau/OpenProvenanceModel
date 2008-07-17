package org.openprovenance.model;

public class OPMUtilities {

    private OPMFactory of=new OPMFactory();

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
        return null;
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


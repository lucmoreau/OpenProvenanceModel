package org.openprovenance.model;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/** Factory of OPM objects. */

public class OPMFactory implements CommonURIs {

    public static final String packageList=
        "org.openprovenance.model";

    public String getPackageList() {
        return packageList;
    }

    private final static OPMFactory oFactory=new OPMFactory();

    public static OPMFactory getFactory() {
        return oFactory;
    }

    protected ObjectFactory of;

    public OPMFactory() {
        of=new ObjectFactory();
    }

    public ProcessRef newProcessRef(Process p) {
        ProcessRef res=of.createProcessRef();
        res.setRef(p);
        return res;
    }

    public RoleRef newRoleRef(Role p) {
        RoleRef res=of.createRoleRef();
        res.setRef(p);
        return res;
    }

    public AnnotationRef newAnnotationRef(Annotation a) {
        AnnotationRef res=of.createAnnotationRef();
        res.setRef(a);
        return res;
    }

    public ArtifactRef newArtifactRef(Artifact a) {
        ArtifactRef res=of.createArtifactRef();
        res.setRef(a);
        return res;
    }
    public AgentRef newAgentRef(Agent a) {
        AgentRef res=of.createAgentRef();
        res.setRef(a);
        return res;
    }

    public AccountRef newAccountRef(Account acc) {
        AccountRef res=of.createAccountRef();
        res.setRef(acc);
        return res;
    }





    public CausalDependencyRef newCausalDependencyRef(WasGeneratedBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }

    public CausalDependencyRef newCausalDependencyRef(Used edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }
    public CausalDependencyRef newCausalDependencyRef(WasDerivedFrom edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }

    public CausalDependencyRef newCausalDependencyRef(WasControlledBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }
    public CausalDependencyRef newCausalDependencyRef(WasTriggeredBy edge) {
        CausalDependencyRef res=of.createCausalDependencyRef();
        res.setRef(edge);
        return res;
    }



    public Process newProcess(String pr,
                              Collection<Account> accounts) {
        return newProcess(pr,accounts,null);
    }

    public Process newProcess(String pr,
                              Collection<Account> accounts,
                              String label) {
        Process res=of.createProcess();
        res.setId(pr);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        if (label!=null) {
            res.getAnnotation().add(of.createLabel(newLabel(label)));
        }
        return res;
    }

    public Agent newAgent(String ag,
                          Collection<Account> accounts) {
        return newAgent(ag,accounts,null);
    }

    public Agent newAgent(String ag,
                          Collection<Account> accounts,
                          String label) {
        Agent res=of.createAgent();
        res.setId(ag);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        if (label!=null) res.getAnnotation().add(of.createLabel(newLabel(label)));
        return res;
    }

    public Account newAccount(String id) {
        Account res=of.createAccount();
        res.setId(id);
        return res;
    }
    public Account newAccount(String id, String label) {
        Account res=of.createAccount();
        res.setId(id);
        res.getAnnotation().add(of.createLabel(newLabel(label)));
        return res;
    }

    public Label newLabel(String label) {
        Label res=of.createLabel();
        res.setValue(label);
        return res;
    }

    public Value newValue(Object value, String encoding) {
        Value res=of.createValue();
        res.setContent(value);
        res.setEncoding(encoding);
        return res;
    }


    public Profile newProfile(String profile) {
        Profile res=of.createProfile();
        res.setValue(profile);
        return res;
    }


    public PName newPName(String profile) {
        PName res=of.createPName();
        res.setValue(profile);
        return res;
    }


    
    public String getLabel(EmbeddedAnnotation annotation) {
        if (annotation instanceof Label) {
            Label label=(Label) annotation;
            return label.getValue();
        } else {
            for (Property prop: annotation.getProperty()) {
                if (prop.getUri().equals(LABEL_PROPERTY)) {
                    return (String) prop.getValue();
                }
            }
            return null;
        }
    }


    public String getType(EmbeddedAnnotation annotation) {
        if (annotation instanceof Type) {
            Type type=(Type) annotation;
            return type.getValue();
        } else {
            for (Property prop: annotation.getProperty()) {
                if (prop.getUri().equals(TYPE_PROPERTY)) {
                    return (String) prop.getValue();
                }
            }
            return null;
        }
    }

    public Object getValue(EmbeddedAnnotation annotation) {
        if (annotation instanceof Value) {
            Value value=(Value) annotation;
            return value.getContent();
        } else {
            for (Property prop: annotation.getProperty()) {
                if (prop.getUri().equals(VALUE_PROPERTY)) {
                    return prop.getValue();
                }
            }
            return null;
        }
    }

    public String getProfile(EmbeddedAnnotation annotation) {
        if (annotation instanceof Profile) {
            Profile profile=(Profile) annotation;
            return profile.getValue();
        } else {
            for (Property prop: annotation.getProperty()) {
                if (prop.getUri().equals(PROFILE_PROPERTY)) {
                    return (String) prop.getValue();
                }
            }
            return null;
        }
    }

    public String getPname(EmbeddedAnnotation annotation) {
        if (annotation instanceof PName) {
            PName pname=(PName) annotation;
            return pname.getValue();
        } else {
            for (Property prop: annotation.getProperty()) {
                if (prop.getUri().equals(PNAME_PROPERTY)) {
                    return (String) prop.getValue();
                }
            }
            return null;
        }
    }



    /** Return the value of the value property in the first annotation. */

    public Object getValue(List<JAXBElement<? extends EmbeddedAnnotation>> annotations) {
        for (JAXBElement<? extends EmbeddedAnnotation> jann: annotations) {
            EmbeddedAnnotation ann=jann.getValue();
            Object value=getValue(ann);
            if (value!=null) return value;
        }
        return null;
    }



    /** Return the value of the label property in the first annotation. */
    public String getLabel(List<JAXBElement<? extends EmbeddedAnnotation>> annotations) {
        for (JAXBElement<? extends EmbeddedAnnotation> jann: annotations) {
            EmbeddedAnnotation ann=jann.getValue();
            String label=getLabel(ann);
            if (label!=null) return label;
        }
        return null;
    }


    /** Return the value of the type property in the first annotation. */

    public String getType(List<JAXBElement<? extends EmbeddedAnnotation>> annotations) {
        for (JAXBElement<? extends EmbeddedAnnotation> jann: annotations) {
            EmbeddedAnnotation ann=jann.getValue();
            String type=getType(ann);
            if (type!=null) return type;
        }
        return null;
    }


    /** Return the value of the profile property in the first annotation. */
    public String getProfile(List<JAXBElement<? extends EmbeddedAnnotation>> annotations) {
        for (JAXBElement<? extends EmbeddedAnnotation> jann: annotations) {
            EmbeddedAnnotation ann=jann.getValue();
            String profile=getProfile(ann);
            if (profile!=null) return profile;
        }
        return null;
    }

    /** Return the value of the pname property in the first annotation. */
    public String getPname(List<JAXBElement<? extends EmbeddedAnnotation>> annotations) {
        for (JAXBElement<? extends EmbeddedAnnotation> jann: annotations) {
            EmbeddedAnnotation ann=jann.getValue();
            String pname=getPname(ann);
            if (pname!=null) return pname;
        }
        return null;
    }


    /** Generic accessor for annotable entities. */
    public String getLabel(Annotable annotable) {
        return getLabel(annotable.getAnnotation());
    }

    /** Generic accessor for annotable entities. */
    public String getType(Annotable annotable) {
        return getType(annotable.getAnnotation());
    }

    /** Generic accessor for annotable entities. */
    public String getProfile(Annotable annotable) {
        return getProfile(annotable.getAnnotation());
    }

    /** Generic accessor for annotable entities. */
    public String getPname(Annotable annotable) {
        return getPname(annotable.getAnnotation());
    }


    public Type newType(String type) {
        Type res=of.createType();
        res.setValue(type);
        return res;
    }

    public void addValue(Artifact annotable, Object value, String encoding) {
        annotable.getAnnotation().add(of.createValue(newValue(value,encoding)));
    }


    public void addAnnotation(Annotable annotable, Value ann) {
        annotable.getAnnotation().add(of.createValue(ann));
    }

    public void addAnnotation(Annotable annotable, Profile ann) {
        annotable.getAnnotation().add(of.createProfile(ann));
    }

    public void addAnnotation(Annotable annotable, PName ann) {
        annotable.getAnnotation().add(of.createPname(ann));
    }

    public void addAnnotation(Annotable annotable, EmbeddedAnnotation ann) {
        annotable.getAnnotation().add(of.createAnnotation(ann));
    }

    public void addAnnotation(Annotable annotable, JAXBElement<? extends EmbeddedAnnotation> ann) {
        annotable.getAnnotation().add(ann);
    }
    public void addAnnotations(Annotable annotable, List<JAXBElement<? extends EmbeddedAnnotation>> anns) {
        annotable.getAnnotation().addAll(anns);
    }

    public void expandAnnotation(EmbeddedAnnotation ann) {
        if (ann instanceof Label) {
            Label label=(Label) ann;
            String labelValue=label.getValue();
            ann.getProperty().add(newProperty(LABEL_PROPERTY,labelValue));
        }
        if (ann instanceof Type) {
            Type type=(Type) ann;
            String typeValue=type.getValue();
            ann.getProperty().add(newProperty(TYPE_PROPERTY,typeValue));
        }
    }


    public JAXBElement<? extends EmbeddedAnnotation> compactAnnotation(EmbeddedAnnotation ann) {
        if (ann instanceof Label) {
            Label label=(Label) ann;
            return of.createLabel(label);
        }
        if (ann instanceof Type) {
            Type type=(Type) ann;
            return of.createType(type);
        }
        if (ann instanceof Profile) {
            Profile profile=(Profile) ann;
            return of.createProfile(profile);
        }
        if (ann instanceof Value) {
            Value value=(Value) ann;
            return of.createValue(value);
        }
        if (ann instanceof PName) {
            PName pname=(PName) ann;
            return of.createPname(pname);
        }
        List<Property> properties=ann.getProperty();
        if (properties.size()==1) {
            Property prop=properties.get(0);
            if (prop.getUri().equals(LABEL_PROPERTY)) {
                Label label=newLabel((String)prop.getValue());
                setIdForCompactAnnotation(label,ann.getId());
                return  of.createLabel(label);
            }
            if (prop.getUri().equals(TYPE_PROPERTY)) {
                Type type=newType((String)prop.getValue());
                setIdForCompactAnnotation(type,ann.getId());
                return  of.createType(type);
            }
            if (prop.getUri().equals(PROFILE_PROPERTY)) {
                Profile profile=newProfile((String)prop.getValue());
                setIdForCompactAnnotation(profile,ann.getId());
                return  of.createProfile(profile);
            }
            if (prop.getUri().equals(PNAME_PROPERTY)) {
                PName pname=newPName((String)prop.getValue());
                setIdForCompactAnnotation(pname,ann.getId());
                return  of.createPname(pname);
            }
        }
        else if (properties.size()==2) {
            if ((properties.get(0).getUri().equals(VALUE_PROPERTY))
                &&
                (properties.get(1).getUri().equals(ENCODING_PROPERTY))) {
                Value value=newValue(properties.get(0).getValue(),
                                     (String)properties.get(1).getValue());
                setIdForCompactAnnotation(value,ann.getId());
                return  of.createValue(value);
            } else if ((properties.get(1).getUri().equals(VALUE_PROPERTY))
                       &&
                       (properties.get(0).getUri().equals(ENCODING_PROPERTY))) {
                Value value=newValue(properties.get(1).getValue(),
                                     (String)properties.get(0).getValue());
                setIdForCompactAnnotation(value,ann.getId());
                return  of.createValue(value);
            }
        }

        return of.createAnnotation(ann);
    }

    public boolean compactId=false;
    
    public void setIdForCompactAnnotation(EmbeddedAnnotation ann, String id) {
        if (compactId) ann.setId(id);
    }


    public void addAnnotation(Annotable annotable, List<EmbeddedAnnotation> anns) {
        List<JAXBElement<? extends EmbeddedAnnotation>> annotations=annotable.getAnnotation();
        for (EmbeddedAnnotation ann: anns) {        
            annotations.add(of.createAnnotation(ann));
        }
    }



    public void addCompactAnnotation(Annotable annotable, List<EmbeddedAnnotation> anns) {
        List<JAXBElement<? extends EmbeddedAnnotation>> annotations=annotable.getAnnotation();
        for (EmbeddedAnnotation ann: anns) {        
            annotations.add(compactAnnotation(ann));
        }
    }



    public Overlaps newOverlaps(Collection<Account> accounts) {
        Overlaps res=of.createOverlaps();
        LinkedList ll=new LinkedList();
        int i=0;
        for (Account acc: accounts) {
            if (i==2) break;
            ll.add(newAccountRef(acc));
            i++;
        }
        res.getAccount().addAll(ll);
        return res;
    }
        
    public Overlaps newOverlaps(AccountRef aid1,AccountRef aid2) {
        Overlaps res=of.createOverlaps();
        res.getAccount().add(aid1);
        res.getAccount().add(aid2);
        return res;
    }
        

    public Role newRole(String value) {
        Role res=of.createRole();
        res.setValue(value);
        return res;
    }

    public Role newRole(String id, String value) {
        Role res=of.createRole();
        res.setId(id);
        res.setValue(value);
        return res;
    }

    public Artifact newArtifact(String id,
                                Collection<Account> accounts) {
        return newArtifact(id,accounts,null);
    }

    public Artifact newArtifact(String id,
                                Collection<Account> accounts,
                                String label) {
        Artifact res=of.createArtifact();
        res.setId(id);
        if ((accounts !=null) && (accounts.size()!=0)) {
            LinkedList ll=new LinkedList();
            for (Account acc: accounts) {
                ll.add(newAccountRef(acc));
            }
            res.getAccount().addAll(ll);
        }
        if (label!=null) {
            res.getAnnotation().add(of.createLabel(newLabel(label)));
        }
        return res;
    }

    public Used newUsed(ProcessRef pid,
                        Role role,
                        ArtifactRef aid,
                        Collection<AccountRef> accounts) {
        Used res=of.createUsed();
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(aid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }

    public Used newUsed(Process p,
                        Role role,
                        Artifact a,
                        Collection<Account> accounts) {
        ProcessRef pid=newProcessRef(p);
        ArtifactRef aid=newArtifactRef(a);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return newUsed(pid,role,aid,ll);
    }

    public Used newUsed(String id,
                        Process p,
                        Role role,
                        Artifact a,
                        Collection<Account> accounts) {
        Used res=newUsed(p,role,a,accounts);
        res.setId(id);
        return res;
    }
    public Used newUsed(String id,
                        Process p,
                        Role role,
                        Artifact a,
                        String type,
                        Collection<Account> accounts) {
        Used res=newUsed(id,p,role,a,accounts);
        addAnnotation(res,of.createType(newType(type)));
        return res;
    }

    public Used newUsed(Used u) {
        Used u1=newUsed(u.getEffect(),
                        u.getRole(),
                        u.getCause(),
                        u.getAccount());
        u1.setId(u.getId());
        u1.getAnnotation().addAll(u.getAnnotation());
        return u1;
    }

    public WasControlledBy newWasControlledBy(WasControlledBy c) {
        WasControlledBy wcb=newWasControlledBy(c.getEffect(),
                                               c.getRole(),
                                               c.getCause(),
                                               c.getAccount());
        wcb.setId(c.getId());
        wcb.getAnnotation().addAll(c.getAnnotation());
        return wcb;
    }

    public WasGeneratedBy newWasGeneratedBy(WasGeneratedBy g) {
        WasGeneratedBy wgb=newWasGeneratedBy(g.getEffect(),
                                             g.getRole(),
                                             g.getCause(),
                                             g.getAccount());
        wgb.setId(g.getId());
        wgb.getAnnotation().addAll(g.getAnnotation());
        return wgb;
    }

    public WasDerivedFrom newWasDerivedFrom(WasDerivedFrom d) {
        WasDerivedFrom wdf=newWasDerivedFrom(d.getEffect(),
                                             d.getCause(),
                                             d.getAccount());
        wdf.setId(d.getId());
        wdf.getAnnotation().addAll(d.getAnnotation());
        return wdf;
    }

    public WasTriggeredBy newWasTriggeredBy(WasTriggeredBy d) {
        WasTriggeredBy wtb=newWasTriggeredBy(d.getEffect(),
                                             d.getCause(),
                                             d.getAccount());
        wtb.setId(d.getId());
        wtb.getAnnotation().addAll(d.getAnnotation());
        return wtb;
    }



    public WasGeneratedBy newWasGeneratedBy(ArtifactRef aid,
                                            Role role,
                                            ProcessRef pid,
                                            Collection<AccountRef> accounts) {
        WasGeneratedBy res=of.createWasGeneratedBy();
        res.setCause(pid);
        res.setRole(role);
        res.setEffect(aid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }
    public WasGeneratedBy newWasGeneratedBy(Artifact a,
                                            Role role,
                                            Process p,
                                            Collection<Account> accounts) {
        ArtifactRef aid=newArtifactRef(a);
        ProcessRef pid=newProcessRef(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasGeneratedBy(aid,role,pid,ll);
    }


    public WasGeneratedBy newWasGeneratedBy(String id,
                                            Artifact a,
                                            Role role,
                                            Process p,
                                            Collection<Account> accounts) {
        WasGeneratedBy res= newWasGeneratedBy(a,role,p,accounts);
        res.setId(id);
        return res;
    }

    public WasGeneratedBy newWasGeneratedBy(String id,
                                            Artifact a,
                                            Role role,
                                            Process p,
                                            String type,
                                            Collection<Account> accounts) {
        WasGeneratedBy wgb=newWasGeneratedBy(id,a,role,p,accounts);
        addAnnotation(wgb,of.createType(newType(type)));
        return wgb;
    }


    public WasControlledBy newWasControlledBy(ProcessRef pid,
                                              Role role,
                                              AgentRef agid,
                                              Collection<AccountRef> accounts) {
        WasControlledBy res=of.createWasControlledBy();
        res.setEffect(pid);
        res.setRole(role);
        res.setCause(agid);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }
        return res;
    }


    public WasControlledBy newWasControlledBy(Process p,
                                              Role role,
                                              Agent ag,
                                              Collection<Account> accounts) {
        AgentRef agid=newAgentRef(ag);
        ProcessRef pid=newProcessRef(p);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasControlledBy(pid,role,agid,ll);
    }

    public WasControlledBy newWasControlledBy(String id,
                                              Process p,
                                              Role role,
                                              Agent ag,
                                              String type,
                                              Collection<Account> accounts) {
        WasControlledBy wcb=newWasControlledBy(p,role,ag,accounts);
        wcb.setId(id);
        addAnnotation(wcb,of.createType(newType(type)));
        return wcb;
    }


    public WasDerivedFrom newWasDerivedFrom(ArtifactRef aid1,
                                            ArtifactRef aid2,
                                            Collection<AccountRef> accounts) {
        WasDerivedFrom res=of.createWasDerivedFrom();
        res.setCause(aid2);
        res.setEffect(aid1);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }

        return res;
    }

    public WasDerivedFrom newWasDerivedFrom(Artifact a1,
                                            Artifact a2,
                                            Collection<Account> accounts) {
        ArtifactRef aid1=newArtifactRef(a1);
        ArtifactRef aid2=newArtifactRef(a2);
        LinkedList ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasDerivedFrom(aid1,aid2,ll);
    }


    public WasDerivedFrom newWasDerivedFrom(String id,
                                            Artifact a1,
                                            Artifact a2,
                                            String type,
                                            Collection<Account> accounts) {
        WasDerivedFrom wdf=newWasDerivedFrom(a1,a2,accounts);
        wdf.setId(id);
        addAnnotation(wdf,of.createType(newType(type)));
        return wdf;
    }



    public WasTriggeredBy newWasTriggeredBy(ProcessRef pid1,
                                            ProcessRef pid2,
                                            Collection<AccountRef> accounts) {
        WasTriggeredBy res=of.createWasTriggeredBy();
        res.setEffect(pid1);
        res.setCause(pid2);
        if ((accounts !=null) && (accounts.size()!=0)) {
            res.getAccount().addAll(accounts);
        }

        return res;
    }

    public WasTriggeredBy newWasTriggeredBy(Process p1,
                                            Process p2,
                                            Collection<Account> accounts) {
        ProcessRef pid1=newProcessRef(p1);
        ProcessRef pid2=newProcessRef(p2);
        LinkedList<AccountRef> ll=new LinkedList();
        for (Account acc: accounts) {
            ll.add(newAccountRef(acc));
        }
        return  newWasTriggeredBy(pid1,pid2,ll);
    }

    public WasTriggeredBy newWasTriggeredBy(String id,
                                            Process p1,
                                            Process p2,
                                            String type,
                                            Collection<Account> accounts) {
        WasTriggeredBy wtb=newWasTriggeredBy(p1,p2,accounts);
        wtb.setId(id);
        addAnnotation(wtb,of.createType(newType(type)));
        return wtb;
    }


    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    Object value,
                                                    Collection<Account> accs) {
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newEmbeddedAnnotation(id,property,value,ll,null);
    }

    public Annotation newAnnotation(String id,
                                    Artifact a,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        ArtifactRef aid=newArtifactRef(a);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,aid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    Process p,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        ProcessRef pid=newProcessRef(p);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,pid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    Annotation a,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        AnnotationRef aid=newAnnotationRef(a);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,aid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    WasDerivedFrom edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    Used edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasGeneratedBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasControlledBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }
    public Annotation newAnnotation(String id,
                                    WasTriggeredBy edge,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        CausalDependencyRef cid=newCausalDependencyRef(edge);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,cid,property,value,ll);
    }

    public Annotation newAnnotation(String id,
                                    Role role,
                                    String property,
                                    Object value,
                                    Collection<Account> accs) {
        RoleRef rid=newRoleRef(role);
        LinkedList<AccountRef> ll=new LinkedList();
        if (accs!=null) {
            for (Account acc: accs) {
                ll.add(newAccountRef(acc));
            }
        }
        return newAnnotation(id,rid,property,value,ll);
    }

    public Property newProperty(String property,
                                Object value) {
        Property res=of.createProperty();
        res.setUri(property);
        res.setValue(value);
        return res;
    }


    public Annotation newAnnotation(String id,
                                    Ref ref,
                                    String property,
                                    Object value,
                                    Collection<AccountRef> accs) {
        Annotation res=of.createAnnotation();
        res.setId(id);
        res.setLocalSubject(ref.getRef());
        res.getProperty().add(newProperty(property,value));
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        return res;
    }

    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    String property,
                                                    Object value,
                                                    Collection<AccountRef> accs,
                                                    Object dummyParameterForAvoidingSameErasure) {
        EmbeddedAnnotation res=of.createEmbeddedAnnotation();
        res.setId(id);
        res.getProperty().add(newProperty(property,value));
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        return res;
    }
    public EmbeddedAnnotation newEmbeddedAnnotation(String id,
                                                    List<Property> properties,
                                                    Collection<AccountRef> accs,
                                                    Object dummyParameterForAvoidingSameErasure) {
        EmbeddedAnnotation res=of.createEmbeddedAnnotation();
        res.setId(id);
        if (properties!=null) {
            res.getProperty().addAll(properties);
        }
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        return res;
    }


    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<Object> lks) {
        return newOPMGraph(accs,ops,ps,as,ags,lks,null);
    }

    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Collection<Overlaps> ops,
                                Collection<Process> ps,
                                Collection<Artifact> as,
                                Collection<Agent> ags,
                                Collection<Object> lks,
                                Collection<Annotation> anns)
    {
        OPMGraph res=of.createOPMGraph();
        if (accs!=null) {
            Accounts aaccs=of.createAccounts();
            aaccs.getAccount().addAll(accs);
            if (ops!=null) 
                aaccs.getOverlaps().addAll(ops);
            res.setAccounts(aaccs);
            
        }
        if (ps!=null) {
            Processes pps=of.createProcesses();
            pps.getProcess().addAll(ps);
            res.setProcesses(pps);
        }
        if (as!=null) {
            Artifacts aas=of.createArtifacts();
            aas.getArtifact().addAll(as);
            res.setArtifacts(aas);
        }
        if (ags!=null) {
            Agents aags=of.createAgents();
            aags.getAgent().addAll(ags);
            res.setAgents(aags);
        }
        if (lks!=null) {
            CausalDependencies ccls=of.createCausalDependencies();
            ccls.getUsedOrWasGeneratedByOrWasTriggeredBy().addAll(lks);
            res.setCausalDependencies(ccls);
        }

        if (anns!=null) {
            Annotations l=of.createAnnotations();
            l.getAnnotation().addAll(anns);
            res.setAnnotations(l);
        }
        return res;
    }

    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Overlaps [] ovs,
                                Process [] ps,
                                Artifact [] as,
                                Agent [] ags,
                                Object [] lks) 
    {

        return newOPMGraph(accs,
                           ((ovs==null) ? null : Arrays.asList(ovs)),
                           ((ps==null) ? null : Arrays.asList(ps)),
                           ((as==null) ? null : Arrays.asList(as)),
                           ((ags==null) ? null : Arrays.asList(ags)),
                           ((lks==null) ? null : Arrays.asList(lks)));
    }
    public OPMGraph newOPMGraph(Collection<Account> accs,
                                Overlaps [] ovs,
                                Process [] ps,
                                Artifact [] as,
                                Agent [] ags,
                                Object [] lks,
                                Annotation [] anns) 
    {

        return newOPMGraph(accs,
                           ((ovs==null) ? null : Arrays.asList(ovs)),
                           ((ps==null) ? null : Arrays.asList(ps)),
                           ((as==null) ? null : Arrays.asList(as)),
                           ((ags==null) ? null : Arrays.asList(ags)),
                           ((lks==null) ? null : Arrays.asList(lks)),
                           ((anns==null) ? null : Arrays.asList(anns)));
    }

    public OPMGraph newOPMGraph(Accounts accs,
                                Processes ps,
                                Artifacts as,
                                Agents ags,
                                CausalDependencies lks)
    {
        OPMGraph res=of.createOPMGraph();
        res.setAccounts(accs);
        res.setProcesses(ps);
        res.setArtifacts(as);
        res.setAgents(ags);
        res.setCausalDependencies(lks);
        return res;
    }

    public OPMGraph newOPMGraph(Accounts accs,
                                Processes ps,
                                Artifacts as,
                                Agents ags,
                                CausalDependencies lks,
                                Annotations anns)
    {
        OPMGraph res=of.createOPMGraph();
        res.setAccounts(accs);
        res.setProcesses(ps);
        res.setArtifacts(as);
        res.setAgents(ags);
        res.setCausalDependencies(lks);
        res.setAnnotations(anns);
        return res;
    }

    public OPMGraph newOPMGraph(OPMGraph graph) {
        return newOPMGraph(graph.getAccounts(),
                           graph.getProcesses(),
                           graph.getArtifacts(),
                           graph.getAgents(),
                           graph.getCausalDependencies(),
                           graph.getAnnotations());
    }


    public Accounts newAccounts(Collection<Account> accs,
                                Collection<Overlaps> ovlps) {
        Accounts res=of.createAccounts();
        if (accs!=null) {
            res.getAccount().addAll(accs);
        }
        if (ovlps!=null) {
            res.getOverlaps().addAll(ovlps);
        }
        return res;
    }

//     public Encoding newEncoding(String encoding) {
//         Encoding res=of.createEncoding();
//         res.setValue(encoding);
//         return res;
//     }
//     public String getEncoding(EmbeddedAnnotation annotation) {
//         if (annotation instanceof Encoding) {
//             Encoding encoding=(Encoding) annotation;
//             return encoding.getValue();
//         } else {
//             for (Property prop: annotation.getProperty()) {
//                 if (prop.equals(ENCODING_PROPERTY)) {
//                     return (String) prop.getValue();
//                 }
//             }
//             return null;
//         }
//     }

            
}


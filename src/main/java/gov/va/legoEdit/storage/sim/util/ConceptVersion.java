package gov.va.legoEdit.storage.sim.util;

import gov.va.legoEdit.model.schemaModel.Concept;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import org.ihtsdo.tk.api.AnalogBI;
import org.ihtsdo.tk.api.ComponentChroncileBI;
import org.ihtsdo.tk.api.ContradictionException;
import org.ihtsdo.tk.api.NidSetBI;
import org.ihtsdo.tk.api.PositionBI;
import org.ihtsdo.tk.api.ProcessComponentChronicleBI;
import org.ihtsdo.tk.api.TerminologySnapshotDI;
import org.ihtsdo.tk.api.blueprint.ConceptCB;
import org.ihtsdo.tk.api.blueprint.DescCAB;
import org.ihtsdo.tk.api.blueprint.InvalidCAB;
import org.ihtsdo.tk.api.changeset.ChangeSetGenerationPolicy;
import org.ihtsdo.tk.api.changeset.ChangeSetGenerationThreadingPolicy;
import org.ihtsdo.tk.api.conattr.ConAttrChronicleBI;
import org.ihtsdo.tk.api.conattr.ConAttrVersionBI;
import org.ihtsdo.tk.api.concept.ConceptChronicleBI;
import org.ihtsdo.tk.api.concept.ConceptVersionBI;
import org.ihtsdo.tk.api.constraint.ConstraintBI;
import org.ihtsdo.tk.api.constraint.ConstraintCheckType;
import org.ihtsdo.tk.api.coordinate.EditCoordinate;
import org.ihtsdo.tk.api.coordinate.ViewCoordinate;
import org.ihtsdo.tk.api.description.DescriptionChronicleBI;
import org.ihtsdo.tk.api.description.DescriptionVersionBI;
import org.ihtsdo.tk.api.id.IdBI;
import org.ihtsdo.tk.api.media.MediaChronicleBI;
import org.ihtsdo.tk.api.media.MediaVersionBI;
import org.ihtsdo.tk.api.refex.RefexChronicleBI;
import org.ihtsdo.tk.api.refex.RefexVersionBI;
import org.ihtsdo.tk.api.relationship.RelationshipChronicleBI;
import org.ihtsdo.tk.api.relationship.RelationshipVersionBI;
import org.ihtsdo.tk.api.relationship.group.RelGroupVersionBI;

/**
 * Just a hack class to let me define a concept in the WB API when it doesn't exist in the DB for test purposes.
 * This shouldn't be used when the code is promoted up to normal useage...
 * @author darmbrust
 */
@SuppressWarnings("rawtypes")
public class ConceptVersion implements ConceptVersionBI
{
    Concept concept_;

    public ConceptVersion(Concept concept)
    {
        concept_ = concept;
    }
    
    @Override
    public boolean stampIsInRange(int min, int max)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toUserString(TerminologySnapshotDI snapshot) throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Integer> getAllNidsForVersion() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getAuthorNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getModuleNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PositionBI getPosition() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getStampNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getStatusNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isActive(NidSetBI allowedStatusNids) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActive(ViewCoordinate vc) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isUncommitted()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBaselineGeneration()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean versionsEqual(ViewCoordinate vc1, ViewCoordinate vc2, Boolean compareAuthoring)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAnnotation(RefexChronicleBI<?> annotation) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toUserString()
    {
        return concept_.getDesc();
    }

    @Override
    public Collection<? extends IdBI> getAdditionalIds() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends IdBI> getAllIds() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexChronicleBI<?>> getAnnotations() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getConceptNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentAnnotationMembers(ViewCoordinate xyz) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends RefexVersionBI<?>> Collection<T> getCurrentAnnotationMembers(ViewCoordinate xyz, Class<T> cls)
            throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentAnnotationMembers(ViewCoordinate xyz, int refexNid)
            throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends RefexVersionBI<?>> Collection<T> getCurrentAnnotationMembers(ViewCoordinate xyz, int refexNid,
            Class<T> cls) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentRefexMembers(ViewCoordinate xyz, int refsetNid)
            throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentRefexes(ViewCoordinate xyz) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Deprecated
    public Collection<? extends RefexVersionBI<?>> getCurrentRefexes(ViewCoordinate xyz, int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getInactiveRefexes(ViewCoordinate xyz) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UUID getPrimUuid()
    {
        return UUID.fromString(concept_.getUuid());
    }

    @Override
    public Collection<? extends RefexChronicleBI<?>> getRefexMembers(int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexChronicleBI<?>> getRefexes() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Deprecated
    public Collection<? extends RefexChronicleBI<?>> getRefexes(int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UUID> getUUIDs()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasCurrentAnnotationMember(ViewCoordinate xyz, int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasCurrentRefexMember(ViewCoordinate xyz, int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long getTime()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPathNid()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void cancel() throws IOException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean commit(ChangeSetGenerationPolicy changeSetPolicy,
            ChangeSetGenerationThreadingPolicy changeSetWriterThreading) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toLongString()
    {
        return concept_.getDesc()  + " : " + concept_.getUuid() + " : " + concept_.getSctid();
    }

    @Override
    public ConAttrChronicleBI getConAttrs() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefexVersionBI<?> getCurrentRefsetMemberForComponent(ViewCoordinate vc, int componentNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ComponentChroncileBI<?> getComponent(int nid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentRefsetMembers(ViewCoordinate vc) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentRefsetMembers(ViewCoordinate vc, Long cutoffTime)
            throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionChronicleBI> getDescs() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLastModificationSequence()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<? extends MediaChronicleBI> getMedia() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefexChronicleBI<?> getRefsetMemberForComponent(int componentNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexChronicleBI<?>> getRefsetMembers() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelGroupVersionBI> getRelGroups(ViewCoordinate vc) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipChronicleBI> getRelsIncoming() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipChronicleBI> getRelsOutgoing() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasCurrentRefsetMemberForComponent(ViewCoordinate vc, int componentNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAnnotationStyleRefex() throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAnnotationStyleRefex(boolean annotationSyleRefex)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void processComponentChronicles(ProcessComponentChronicleBI processor) throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ConceptVersionBI getVersion(ViewCoordinate c) throws ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getVersions(ViewCoordinate c)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getVersions()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Integer> getAllStampNids() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<PositionBI> getPositions() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConceptVersionBI getPrimordialVersion()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean makeAdjudicationAnalogs(EditCoordinate ec, ViewCoordinate vc) throws Exception
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ConceptChronicleBI getEnclosingConcept()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean satisfies(ConstraintBI constraint, ConstraintCheckType subjectCheck,
            ConstraintCheckType propertyCheck, ConstraintCheckType valueCheck) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ConceptChronicleBI getChronicle()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConAttrVersionBI getConAttrsActive() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getCurrentRefexMembers(int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefexChronicleBI<?> getCurrentRefsetMemberForComponent(int componentNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Deprecated
    public Collection<? extends RefexVersionBI<?>> getCurrentRefsetMembers() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getDescsActive() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getDescsActive(int typeNid) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getDescsActive(NidSetBI typeNids) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getFsnDescsActive() throws IOException
    {
        ArrayList<DescriptionVersionBI> result = new ArrayList<>();
        result.add(new DescriptionVersionBI()
        {

            @Override
            public int getTypeNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean stampIsInRange(int min, int max)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String toUserString(TerminologySnapshotDI snapshot) throws IOException, ContradictionException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Integer> getAllNidsForVersion() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getAuthorNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getModuleNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ComponentChroncileBI getChronicle()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public PositionBI getPosition() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getStampNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getStatusNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean isActive(NidSetBI allowedStatusNids) throws IOException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isActive(ViewCoordinate vc) throws IOException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isUncommitted()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isBaselineGeneration()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean versionsEqual(ViewCoordinate vc1, ViewCoordinate vc2, Boolean compareAuthoring)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean addAnnotation(RefexChronicleBI<?> annotation) throws IOException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String toUserString()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends IdBI> getAdditionalIds() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends IdBI> getAllIds() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexChronicleBI<?>> getAnnotations() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getConceptNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Collection<? extends RefexVersionBI<?>> getCurrentAnnotationMembers(ViewCoordinate xyz)
                    throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T extends RefexVersionBI<?>> Collection<T> getCurrentAnnotationMembers(ViewCoordinate xyz,
                    Class<T> cls) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexVersionBI<?>> getCurrentAnnotationMembers(ViewCoordinate xyz, int refexNid)
                    throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public <T extends RefexVersionBI<?>> Collection<T> getCurrentAnnotationMembers(ViewCoordinate xyz,
                    int refexNid, Class<T> cls) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexVersionBI<?>> getCurrentRefexMembers(ViewCoordinate xyz, int refsetNid)
                    throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexVersionBI<?>> getCurrentRefexes(ViewCoordinate xyz) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            @Deprecated
            public Collection<? extends RefexVersionBI<?>> getCurrentRefexes(ViewCoordinate xyz, int refsetNid)
                    throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexVersionBI<?>> getInactiveRefexes(ViewCoordinate xyz) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public UUID getPrimUuid()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexChronicleBI<?>> getRefexMembers(int refsetNid) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends RefexChronicleBI<?>> getRefexes() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            @Deprecated
            public Collection<? extends RefexChronicleBI<?>> getRefexes(int refsetNid) throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<UUID> getUUIDs()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean hasCurrentAnnotationMember(ViewCoordinate xyz, int refsetNid) throws IOException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean hasCurrentRefexMember(ViewCoordinate xyz, int refsetNid) throws IOException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public long getTime()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getPathNid()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public DescriptionVersionBI getVersion(ViewCoordinate c) throws ContradictionException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends DescriptionVersionBI> getVersions(ViewCoordinate c)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends DescriptionVersionBI> getVersions()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<Integer> getAllStampNids() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Set<PositionBI> getPositions() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public DescriptionVersionBI getPrimordialVersion()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean makeAdjudicationAnalogs(EditCoordinate ec, ViewCoordinate vc) throws Exception
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public ConceptChronicleBI getEnclosingConcept()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public AnalogBI makeAnalog(int statusNid, long time, int authorNid, int moduleNid, int pathNid)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getText()
            {
                return concept_.getDesc();
            }

            @Override
            public boolean isInitialCaseSignificant()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String getLang()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public DescCAB makeBlueprint(ViewCoordinate vc) throws IOException, ContradictionException, InvalidCAB
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean matches(Pattern p)
            {
                // TODO Auto-generated method stub
                return false;
            }
        });
        return result;
    }

    @Override
    public DescriptionVersionBI getFullySpecifiedDescription() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends MediaVersionBI> getMediaActive() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<List<Integer>> getNidPathsToRoot() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getPrefDescsActive() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescriptionVersionBI getPreferredDescription() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RefexVersionBI<?>> getRefsetMembersActive() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelGroupVersionBI> getRelGroups() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipVersionBI> getRelsIncomingActive() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipVersionBI> getRelsIncomingActiveIsa() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOrigins() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOrigins(int typeNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOrigins(NidSetBI typeNids) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOriginsActive() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOriginsActive(int typeNid) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOriginsActive(NidSetBI typeNids) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOriginsActiveIsa() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsIncomingOriginsIsa() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipVersionBI> getRelsOutgoingActive() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends RelationshipVersionBI> getRelsOutgoingActiveIsa() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinations() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinations(int typeNid) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinations(NidSetBI typeNids) throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinationsActive() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinationsActive(int typeNid) throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinationsActive(NidSetBI typeNids)
            throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinationsActiveIsa() throws IOException,
            ContradictionException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends ConceptVersionBI> getRelsOutgoingDestinationsIsa() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRelsOutgoingDestinationsNidsActiveIsa() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends DescriptionVersionBI> getSynonyms() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ViewCoordinate getViewCoordinate()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasAnnotationMemberActive(int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasChildren() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasHistoricalRels() throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasRefexMemberActive(int refsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasRefsetMemberForComponentActive(int componentNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActive() throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildOf(ConceptVersionBI child) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isKindOf(ConceptVersionBI parentKind) throws IOException, ContradictionException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLeaf() throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isMember(int evalRefsetNid) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ConceptCB makeBlueprint() throws IOException, ContradictionException, InvalidCAB
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConceptCB makeBlueprint(ViewCoordinate vc) throws IOException, ContradictionException, InvalidCAB
    {
        // TODO Auto-generated method stub
        return null;
    }

}

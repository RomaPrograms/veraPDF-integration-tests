/**
 * 
 */
package org.verapdf.pdfa.qa;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.verapdf.ReleaseDetails;
import org.verapdf.component.ComponentDetails;
import org.verapdf.component.Components;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *
 */
@XmlRootElement(name="resultSetDetails")
public class ResultSetDetailsImpl implements ResultSetDetails {
    @XmlElement(name = "created")
    private final Date created;
    @XmlElement
    private final ComponentDetails foundryDetails;
    @XmlElementWrapper
    @XmlElement(name = "dependency")
    private final Set<ReleaseDetails> dependencies;
    private ResultSetDetailsImpl() {
        this(Collections.<ReleaseDetails> emptySet());
    }

    private ResultSetDetailsImpl(Set<ReleaseDetails> dependencies) {
        this(new Date(), dependencies);
    }

    private ResultSetDetailsImpl(final Date created,
            final Set<ReleaseDetails> dependencies) {
    	this(created, dependencies, Components.defaultDetails());
    }

    private ResultSetDetailsImpl(final Date created,
            final Set<ReleaseDetails> dependencies, final ComponentDetails details) {
    	super();
        this.created = new Date(created.getTime());
        this.dependencies = new HashSet<>(dependencies);
        this.foundryDetails = details;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public Date getDateCreated() {
        return this.created;
    }
    
    @Override
	public ComponentDetails getFoundryDetails() {
    	return this.foundryDetails;
    }

    /**
     * { @inheritDoc }
     */
    @Override
    public Set<ReleaseDetails> getDependencies() {
        return this.dependencies;
    }

    @Override
    public String toString() {
        return "ResultSetDetailsImpl [created=" + this.created
                + ", dependencies=" + this.dependencies + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.created == null) ? 0 : this.created.hashCode());
        result = prime * result
                + ((this.foundryDetails == null) ? 0 : this.foundryDetails.hashCode());
        result = prime
                * result
                + ((this.dependencies == null) ? 0 : this.dependencies
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultSetDetailsImpl other = (ResultSetDetailsImpl) obj;
        if (this.created == null) {
            if (other.created != null)
                return false;
        } else if (!this.created.equals(other.created))
            return false;
        if (this.foundryDetails == null) {
            if (other.foundryDetails != null)
                return false;
        } else if (!this.foundryDetails.equals(other.foundryDetails))
            return false;
        if (this.dependencies == null) {
            if (other.dependencies != null)
                return false;
        } else if (!this.dependencies.equals(other.dependencies))
            return false;
        return true;
    }

    /**
     * @return a new ResultSetDetails instance
     */
    public static ResultSetDetails getNewInstance(final ComponentDetails details) {
        Set<ReleaseDetails> dependencies = new HashSet<>();
        for (String id : ReleaseDetails.getIds()) {
            dependencies.add(ReleaseDetails.byId(id));
        }
        return new ResultSetDetailsImpl(new Date(), dependencies, details);
    }

    static class Adapter extends
            XmlAdapter<ResultSetDetailsImpl, ResultSetDetails> {
        @Override
        public ResultSetDetails unmarshal(ResultSetDetailsImpl results) {
            return results;
        }

        @Override
        public ResultSetDetailsImpl marshal(ResultSetDetails results) {
            return (ResultSetDetailsImpl) results;
        }
    }
}

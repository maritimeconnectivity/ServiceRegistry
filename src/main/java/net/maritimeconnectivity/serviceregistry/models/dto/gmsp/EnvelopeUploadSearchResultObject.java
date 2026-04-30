package net.maritimeconnectivity.serviceregistry.models.dto.gmsp;

import org.grad.secomv2.core.models.AbstractEnvelope;
import org.grad.secomv2.core.models.ServiceInstanceObject;

import java.util.List;

public class EnvelopeUploadSearchResultObject extends AbstractEnvelope {

    // Class Variables
    private List<ServiceInstanceObject> serviceInstance;
    /**
     * Gets search service result.
     *
     * @return the search service result
     */
    public List<ServiceInstanceObject> getServiceInstance() {
        return serviceInstance;
    }

    /**
     * Sets search service result.
     *
     * @param serviceInstance the search service result
     */
    public void setServiceInstance(List<ServiceInstanceObject> serviceInstance) {
        this.serviceInstance = serviceInstance;
    }


    @Override
    public Object[] getAttributeArray() {
        return new Object[]{
                serviceInstance,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime,
        };
    }
}

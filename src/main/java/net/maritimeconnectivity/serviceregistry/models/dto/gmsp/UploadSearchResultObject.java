package net.maritimeconnectivity.serviceregistry.models.dto.gmsp;

import jakarta.validation.constraints.NotNull;
import org.grad.secomv2.core.models.EnvelopeSearchResultObject;

public class UploadSearchResultObject {

    @NotNull
    private EnvelopeUploadSearchResultObject envelope;
    @NotNull
    private String envelopeSignature;

    /**
     * Get the envelope
     * @return envelope
     */
    public EnvelopeUploadSearchResultObject getEnvelope() {return envelope;}

    /**
     * Sets the envelope
     *
     * @param envelope the envelope search result object
     */
    public void setEnvelope(EnvelopeUploadSearchResultObject envelope) {this.envelope = envelope;}

    /**
     * Gets the envelope signature
     *
     * @return envelopeSignature
     */
    public String getEnvelopeSignature() {return envelopeSignature;}

    /**
     * Sets the envelope signature
     *
     * @param envelopeSignature the envelope signature array
     */
    public void setEnvelopeSignature(String envelopeSignature) {this.envelopeSignature = envelopeSignature;}

}

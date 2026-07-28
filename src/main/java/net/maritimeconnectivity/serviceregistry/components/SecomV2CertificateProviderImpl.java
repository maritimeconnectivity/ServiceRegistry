package net.maritimeconnectivity.serviceregistry.components;

import lombok.extern.slf4j.Slf4j;
import org.grad.secomv2.core.base.DigitalSignatureCertificate;
import org.grad.secomv2.core.base.SecomCertificateProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;

@Component
@Slf4j
@ConditionalOnProperty(name = "secom.security.signingIdentity.enabled", havingValue = "true")
public class SecomV2CertificateProviderImpl implements SecomCertificateProvider {

    private final SecomV2SigningIdentityProvider signingIdentityProvider;

    public SecomV2CertificateProviderImpl(SecomV2SigningIdentityProvider signingIdentityProvider) {
        this.signingIdentityProvider = signingIdentityProvider;
    }

    @Override
    public DigitalSignatureCertificate getDigitalSignatureCertificate() {
        X509Certificate[] chain = signingIdentityProvider.getSigningCertificateChain();

        DigitalSignatureCertificate digitalSignatureCertificate = new DigitalSignatureCertificate();
        digitalSignatureCertificate.setCertificate(chain);
        digitalSignatureCertificate.setPublicKey(chain[0].getPublicKey());
        digitalSignatureCertificate.setRootCertificate(chain[chain.length - 1]);
        return digitalSignatureCertificate;
    }
}


//This is just a nasty namespace hack to access the WebClient secomClient in the derived class
package org.grad.secomv2.springboot3.components;

import lombok.extern.slf4j.Slf4j;
import net.maritimeconnectivity.serviceregistry.models.dto.gmsp.EnvelopeUploadSearchResultObject;
import net.maritimeconnectivity.serviceregistry.models.dto.gmsp.UploadSearchResultObject;
import net.maritimeconnectivity.serviceregistry.services.SecomSearchResultSigningService;
import org.grad.secomv2.core.models.ServiceInstanceObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
public class UploadResultsClient extends SecomClient {

    @Autowired
    SecomSearchResultSigningService secomSearchResultSigningService;

    public UploadResultsClient(URL url, SecomConfigProperties config) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        super(url, config);
        log.info("Initializing UploadResultsClient with URL: {}", url);
    }

    //
    public HttpStatusCode uploadResults(List<ServiceInstanceObject> searchResults) {

        UploadSearchResultObject usrObj = buildUploadSearchResultObject(searchResults);


        ResponseEntity<Void> entity = this.secomClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(usrObj)
                .exchangeToMono(response -> response.toBodilessEntity())
                .block();

        assert entity != null;
        return entity.getStatusCode();
    }

    private UploadSearchResultObject buildUploadSearchResultObject(List<ServiceInstanceObject> searchResults) {

        EnvelopeUploadSearchResultObject envelope = new EnvelopeUploadSearchResultObject();
        envelope.setServiceInstance(searchResults);

        return secomSearchResultSigningService.signUploadSearchResult(envelope);

    }

}

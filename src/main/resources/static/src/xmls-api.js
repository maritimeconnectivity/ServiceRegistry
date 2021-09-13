/******************************************************************************
 *                               XMLS API CALLS                               *
 ******************************************************************************/
class XmlsApi {

    /**
     * The XMLs API Class Constructor.
     */
    constructor() {

    }

    /**
     * API Instance XML validation function.
     *
     * @param  {string} xml             The XML input to be validated
     * @param  {Function} callback      The callback to be used after the AJAX call
     * @param  {Function} errorCallback The error callback to be used if the AJAX call fails
     */
    validateInstanceXml(xml, callback, errorCallback) {
        $.ajax({
            url: `api/xmls/validate/INSTANCE`,
            type: 'POST',
            contentType: 'application/xml',
            dataType: 'json',
            data: xml,
            success: (response, status, more) => {
                var instance = {};
                for (var field in response) {
                    var name = field;
                    var value = response[name];
                    // Translate the G1128 field names to the current model
                    if (name == 'id')
                       name = 'instanceId';
                    else if(name == 'description')
                       name = 'comment';
                    else if(name == 'endpoint')
                       name = 'endpointUri';
                    else if(name == 'implementsServiceDesign') {
                       name = 'designs';
                       value = value['id'];
                    }
                    instance[name] = value
                }
                callback(instance, callback, errorCallback)
            },
            error: (response, status, more) => {
                if(errorCallback) {
                    errorCallback(response, status, more);
                } else {
                    console.error(response)
                }
            }
        });
    }
}
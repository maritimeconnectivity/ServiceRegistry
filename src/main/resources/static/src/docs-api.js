/******************************************************************************
 *                               DOCS API CALLS                               *
 ******************************************************************************/
class DocsApi {

    /**
     * The Docs API Class Constructor.
     */
    constructor() {

    }

    /**
     * API Instance Doc reading function.
     *
     * @param  {number} docId           The ID of the doc to be retrieved
     * @param  {Function} callback      The callback to be used after the AJAX call
     * @param  {Function} errorCallback The error callback to be used if the AJAX call fails
     */
    getDoc(docId, callback, errorCallback) {
        $.ajax({
            url: `api/docs/${docId}`,
            type: 'GET',
            contentType: 'application/json',
            success: callback,
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
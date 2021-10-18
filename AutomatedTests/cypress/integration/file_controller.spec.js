describe('File Controller Tests', () => {
  it('tests the FileNumberSearch successful response', () => {
    cy.request({
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      url: Cypress.env('scss_host') + 'ws/',
      body:
            `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
               <soapenv:Header/>
               <soapenv:Body>
                  <scss:fileNumberSearch>
                     <filter>
                        <courtFileNumber>9950</courtFileNumber>
                        <locationId>16218.0026</locationId>
                        <courtLevelCode>P</courtLevelCode>
                        <courtClassCode>F</courtClassCode>
                     </filter>
                  </scss:fileNumberSearch>
               </soapenv:Body>
            </soapenv:Envelope>`
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/getFileNumberSearchV1.xml').should('eq', response.body)
    })
  })

  it('tests the linkFiles successful response', () => {
    const payload =
         `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
            <soapenv:Header/>
             <soapenv:Body>
            <scss:linkFile>
               <physicalFileId>688</physicalFileId>
               <caseActionNumber>SC000185</caseActionNumber>
            </scss:linkFile>
            </soapenv:Body>
         </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      body: payload

    }).then((response) => {
      expect(response.status).to.eq(200)
      let out = response.body
      out = out.split('<linkId>')
      out[1] = out[1].replace(/\d*/, '')
      out = out[0] + '<linkId>' + out[1]
      cy.readFile('./cypress/ExampleRequests/linkFileV1.xml').should('eq', out)
    })
  })

  it('tests the unlinkFiles successful response', () => {
    const payload =
      `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
         <soapenv:Header/>
         <soapenv:Body>
            <scss:unlinkFile>
               <physicalFileId>688</physicalFileId>
               <caseActionNumber>SC000185</caseActionNumber>
            </scss:unlinkFile>
         </soapenv:Body>
      </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      redirect: 'follow',
      body: payload
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/unlinkFileV1.xml').should('eq', response.body)
    })
  })

  it('tests the fileNumberSearchPublicAccess    successful response', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
    <scss:fileNumbeSearchPublicAccess>
    <filter>
    <courtFileNumber>1017</courtFileNumber>
    <locationId>16218.0026</locationId>
    <courtLevelCode>P</courtLevelCode>
    <courtClassCode>C</courtClassCode>
    </filter>
    </scss:fileNumbeSearchPublicAccess>
    </soapenv:Body>
    </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      body: payload
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/fileNumberSearchPublicAccessV1.xml').should('eq', response.body)
    })
  })

  it('tests the fileNumberSearchPublicAccess sealed record', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
        <scss:fileNumbeSearchPublicAccess>
            <filter>
                <courtFileNumber>147899</courtFileNumber>
                <locationId></locationId>
                <courtLevelCode>S</courtLevelCode>
                <courtClassCode>E</courtClassCode>
            </filter>
        </scss:fileNumbeSearchPublicAccess>
    </soapenv:Body>
</soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      body: payload
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/fileNumberSearchSealed.xml').should('eq', response.body)
    })
  })

  it('tests file search with -1 parameter', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
        <scss:fileNumberSearch>
            <filter>
                <courtFileNumber>9950</courtFileNumber>
                <locationId>-1</locationId>
                <courtLevelCode>P</courtLevelCode>
                <courtClassCode>F</courtClassCode>
            </filter>
        </scss:fileNumberSearch>
    </soapenv:Body>
</soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      },
      body: payload
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/fileSearch-1.xml').should('eq', response.body)
    })
  })
})

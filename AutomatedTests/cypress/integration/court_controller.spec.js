describe('Court Controller Tests', () => {
  it('Test Get Court File Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
       <scss:getCourtFile>
          <physicalFileId>688</physicalFileId>
       </scss:getCourtFile>
    </soapenv:Body>
 </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/getCourtFileV1.xml').should('eq', response.body)
    })
  })

  it('Test Get Court Basics Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
       <scss:getCourtBasics>
          <physicalFileId>8000005</physicalFileId>
       </scss:getCourtBasics>
    </soapenv:Body>
 </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/getCourtBasicsV1.xml').should('eq', response.body)
    })
  })

  it('Test Get Ceis Connect Info Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
       <scss:getCeisConnectInfo/>
    </soapenv:Body>
 </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/getCeisConnectInfoV1.xml').should('eq', response.body)
    })
  })

  it('Test Party Name Search Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
   <soapenv:Header/>
   <soapenv:Body>
      <scss:partyNameSearch>
         <filter>
            <name>James</name>
            <firstName>Jim</firstName>
            <searchType>IND</searchType>
            <agencyId>83.0001</agencyId>
            <courtLevel>P</courtLevel>
            <courtClass>M</courtClass>
            <roleType></roleType>
            <page>1</page>
         </filter>
      </scss:partyNameSearch>
   </soapenv:Body>
</soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/partyNameSearchV1.xml').should('eq', response.body)
    })
  })

  it('Test Get Party Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
   <soapenv:Header/>
   <soapenv:Body>
      <scss:getParties>
         <physicalFileId>777</physicalFileId>
      </scss:getParties>
   </soapenv:Body>
</soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/getPartiesV1.xml').should('eq', response.body)
    })
  })

  it('Test Save Hearing Result Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
       <scss:saveHearingResults>
          <hearingResult>
             <HearingResult>
                <CaseDetails>
                   <CaseTrackingID>11</CaseTrackingID>
                   <CaseFiling>2222</CaseFiling>
                   <CaseAugmentation>
                      <CaseHearing>
                         <CourtEventAppearance>
                            <CourtAppearanceCourt>11</CourtAppearanceCourt>
                            <CourtAppearanceDate>2003-07-11</CourtAppearanceDate>
                            <CourtAppearanceCategoryText>APN</CourtAppearanceCategoryText>
                            <CourtEventSequenceID>?</CourtEventSequenceID>
                            <ActivityStatus>NOT PROCEDING</ActivityStatus>
                            <CancellationStatus>Abandoned</CancellationStatus>
                            <TimeMeasureDetails>
                               <MeasureText>1</MeasureText>
                               <MeasureUnitText>m</MeasureUnitText>
                               <MeasureEstimatedIndicator>true</MeasureEstimatedIndicator>
                            </TimeMeasureDetails>
                         </CourtEventAppearance>
                      </CaseHearing>
                   </CaseAugmentation>
                </CaseDetails>
             </HearingResult>
          </hearingResult>
       </scss:saveHearingResults>
    </soapenv:Body>
 </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/saveHearingResultV1.xml').should('eq', response.body)
    })
  })

  it('Test Party Name Search with Both parameter', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
    <soapenv:Header/>
    <soapenv:Body>
       <scss:partyNameSearch>
          <filter>
             <name>James</name>
             <firstName>Jim</firstName>
             <searchType>BOTH</searchType>
             <agencyId></agencyId>
             <courtLevel>P</courtLevel>
             <courtClass>M</courtClass>
             <page>1</page>
          </filter>
       </scss:partyNameSearch>
    </soapenv:Body>
 </soapenv:Envelope>`

    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/partyNameSearchBoth.xml').should('eq', response.body)
    })
  })
})

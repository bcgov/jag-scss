describe('Health Controller Tests', () => {
  it('Test Actuator Health Api', () => {
    cy.request({
      url: Cypress.env('scss_host') + 'actuator/health',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      expect(response.body.status).to.eq('UP')
    })
  })

  it('Test Health Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
        <soapenv:Header/>
        <soapenv:Body>
          <scss:getHealth/>
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
      cy.readFile('./cypress/ExampleRequests/getHealthV1.xml').should('eq', response.body)
    })
  })

  it('Test Ping Api', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
        <soapenv:Header/>
        <soapenv:Body>
          <scss:getPing/>
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
      cy.readFile('./cypress/ExampleRequests/getPingV1.xml').should('eq', response.body)
    })
  })
})

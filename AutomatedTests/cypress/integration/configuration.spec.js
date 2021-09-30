describe('Configuration Tests', () => {
  it('Test Security enabled', () => {
    const payload = ''
    cy.request({
      url: Cypress.env('scss_host') + 'ws/',
      body: payload,
      method: 'POST',
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(401)
    })
  })

  it('Test Error security dsiabled', () => {
    const payload = ''
    cy.request({
      url: Cypress.env('scss_host') + 'error',
      body: payload,
      method: 'POST',
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(500)
    })
  })

  it('Test WSDL hasnt changed', () => {
    cy.request({
      url: Cypress.env('scss_host') + 'ws/SCSS.Source.CeisScss.ws.provider:CeisScss?WSDL',
      method: 'GET',
      headers: {
        authorization: Cypress.env('scss_token')
      }
    }).then((response) => {
      expect(response.status).to.eq(200)
      cy.readFile('./cypress/ExampleRequests/wsdl.xml').should('eq', response.body)
    })
  })
})

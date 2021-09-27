describe('Notification Controller Tests', () => {
  it('Test getAllNotifications', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
                      <soapenv:Header/>
                      <soapenv:Body>
                         <scss:getAllNotifications/>
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
      cy.readFile('./cypress/ExampleRequests/getAllNotificationsV1.xml').should('eq', response.body)
    })
  })

  it('Test getNotifications', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
                      <soapenv:Header/>
                      <soapenv:Body>
                         <scss:getNotifications>
                            <physicalFileId>2222</physicalFileId>
                         </scss:getNotifications>
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
      cy.readFile('./cypress/ExampleRequests/getNotificationsV1.xml').should('eq', response.body)
    })
  })

  it('Test hasNotification', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
                      <soapenv:Header/>
                      <soapenv:Body>
                         <scss:hasNotification>
                            <physicalFileId>2222</physicalFileId>
                         </scss:hasNotification>
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
      cy.readFile('./cypress/ExampleRequests/hasNotificationsV1.xml').should('eq', response.body)
    })
  })

  it('Test removeNotification', () => {
    const payload = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:scss="http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss">
                      <soapenv:Header/>
                      <soapenv:Body>
                         <scss:removeNotification>
                            <notificationId>89</notificationId>
                         </scss:removeNotification>
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
      cy.readFile('./cypress/ExampleRequests/removeNotificationsV1.xml').should('eq', response.body)
    })
  })
})

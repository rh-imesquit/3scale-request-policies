# Red Hat 3scale OIDC Integration

This lab aims to demonstrate, step by step, the integration between Red Hat 3scale API Management and Red Hat Build of Keycloak (RHBK) using the OpenID Connect (OIDC) protocol. With this setup, it is possible to ensure greater security, standardized access control, and simplified identity management in enterprise environments.

### <h2 style="color: #e5b449;">Versions Used in This Tutorial</h2>

| Component                                   | Version |
|---------------------------------------------|---------|
| Red Hat OpenShift Container Platform        | 4.17    |
| Red Hat 3scale API Management               | 2.15    |
| Red Hat Build of Keycloack                  | 26.0    |

### <h2 style="color: #e5b449;">How-to configure 3scale client in RHBK</h2>

1. In the RHBK console, we will *create a realm named "3scale"*.

2. After creating the realm, we will *create a new client named "3scale-oidc"*.

![Creating 3scale OIDC Client](../images/RHBK/14%20-%20Creating%20client.png)

3. We will use the following settings when defining the client.

| Configuration                |     Value     |
|------------------------------|---------------|
| Client ID                    |  3scale-oidc  |
| Client authentication        |      On       |
| Authorization                |      On       |
| Standard flow                |    Enabled    |
| Direct access grants         |    Enabled    |
| Service accounts roles       |    Enabled    |

<br>

![Creating 3scale OIDC Client](../images/RHBK/15%20-%20Creating%20client.png)

Keep the remaining settings with their default values, proceed using the Next buttons, and finally *click the Save button*.

4. On the 3scale-oidc client screen, select the "Service Account Roles" tab and *click the "Assign role" button*.

![Assigning service accounts to client](../images/RHBK/16%20-%20Assigning%20manage%20clients%20role%20to%20client.png)

5. Since this will be an administrative client for the integration, we need to *assign it the realm-management / manage-clients role*. Select this role and *click the "Assign" button*. Note that the role has been successfully assigned to the client.

![Assigning manage clients role to service accounts](../images/RHBK/17%20-%20Assigning%20manage%20clients%20role%20to%20client.png)

![Manage clients role assigned](../images/RHBK/18%20-%20Role%20assigned.png)

6. Now let's create a user linked to this realm to test the authorization flow. In the left-hand menu, select Users and *click the "Create new user" or "Add user" button*.

![Creating new user](../images/RHBK/19%20-%20Create%20new%20user.png)

7. Fill in the new user's information and *click the "Create" button*.

![Creating new user form](../images/RHBK/20%20-%20Create%20new%20user%20form.png)

8. This user needs a password for authentication. Select the Credentials tab and *click the "Set password" button*. The password can be either temporary or permanent. After creation, it can be reset in the same tab.

![Setting password to user](../images/RHBK/21%20-%20Setting%20password%20to%20user.png)

![Setting password to user](../images/RHBK/22%20-%20Setting%20password%20to%20user.png)

9.  Let's make a test request to obtain the authorization token for this user. To do this, we will need to gather some information spread across different screens.

| Configuration                |    Value                                                                   |
|------------------------------|----------------------------------------------------------------------------|
| grant_type                   |    password                                                                |
| client_id                    |    This information can be found under the Settings tab of the Client.     |
| client_secret                |    This information can be found under the Credentials tab of the Client.  |
| user                         |    This information can be found under the Details tab of the User.        |
| password                     |    This information can be found under the Credentials tab of the User.    |

10. We will perform a POST request to the URL *https://{rhbk host}/realms/{realm name}/protocol/openid-connect/token*. The values listed in the table above must be included in the request body using the form-urlencoded format.

![Sending request to get JWT token](../images/RHBK/23%20-%20Sending%20request%20to%20get%20token.png)

11.  Let's validate and analyze the token obtained in the request response on website [https://jwt.io](https://jwt.io). 

![Verifying valid token](../images/RHBK/24%20-%20Verifying%20valid%20token.png)

<br>

With the configuration successfully validated, we can now proceed to the next step: configuring 3scale to use OIDC as the authentication method.



---

Each time an API consumer subscribes to an API in your Developer Portal, 3scale uses the RH-SSO administrative client that you create in this procedure to create a client for the API consumer application.


### <h2 style="color: #e5b449;">References</h2>

Here are the references and other important links to complement the content presented:

* [Red Hat 3scale API Management - Configuring single sign-on to have a 3scale API Management client](https://docs.redhat.com/en/documentation/red_hat_3scale_api_management/2.15/html/administering_the_api_gateway/integrating-threescale-with-an-openid-connect-identity-provider#configuring-rhsso-to-have-a-threescale-client_3scale-rhsso)
* [Red Hat 3scale API Management - Configuring 3scale API Management to work with single sign-on](https://docs.redhat.com/en/documentation/red_hat_3scale_api_management/2.15/html/administering_the_api_gateway/integrating-threescale-with-an-openid-connect-identity-provider#configuring-threescale-to-work-with-rhsso_3scale-rhsso)
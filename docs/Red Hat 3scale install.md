# Red Hat 3scale Install Guide

## <h2 style="color: #e5b449;">What is</h2>

**Red Hat 3scale API Management** is a powerful API management platform that helps organizations control, distribute, and monitor their APIs in a secure and scalable way. With 3scale, businesses can easily expose their services to internal teams, partners, or external developers while applying access policies, rate limits, and analytics. It enables you to manage the full API lifecycle — from creation and deployment to monitoring and monetization — making your APIs easier to use and more reliable.
<br>

## <h2 style="color: #e5b449;">Versions Used in This Tutorial</h2>

| Component                                   | Version |
|---------------------------------------------|---------|
| Red Hat OpenShift Container Platform        | 4.17    |
| Red Hat 3scale API Management               | 2.15    |

## <h2 style="color: #e5b449;">How-to install</h2>

First, let's create a project named 3scale.

 ![Creating 3scale project](../images/3scale/01%20-%20Creating%203scale%20project.png)

 ![Creating 3scale project](../images/3scale/02%20-%20Creating%203scale%20project.png)

Next, we need to install the operator. To do this, go to the left-hand side menu and access *Operators > Operator Hub*. In the search field, look for 3scale and select the option **Red Hat Integration - 3scale**. Finally, click the *Install button*.

 ![Creating 3scale project](../images/3scale/03%20-%20Installing%203scale%20operator.png)

A form will then be displayed with options to configure the operator installation. Keep all the default options and click the *Install button*.

Wait until the installation is successfully completed. Once finished, access the main panel of the Red Hat 3scale operator. From the available menus, select *API Manager*.

 ![Creating 3scale project](../images/3scale/04%20-%203scale%20operator%20main%20panel.png)

On the next screen, click the *Create APIManager button*. A new screen will open with a form to define the APIManager resource configurations. Switch to the *YAML view*.

You can use the predefined YAML available at: [apimanager.yaml](../infra/3scale/api-manager.yaml)

> **Attention:** You must change the spec.wildcardDomain property to use the domain of your Red Hat OpenShift cluster. Without this correct definition, access to the 3scale admin portal will not work.

Example: 

|                                        |         |
| -------------------------------------- | ------- |
| OpenShift Cluster URL                  | https://console-openshift-console.apps.cluster-nhsnm.nhsnm.sandbox285.opentlc.com  |
|Value to be used in spec.wildcardDomain | **apps.cluster-nhsnm.nhsnm.sandbox285.opentlc.com**  |

After making this configuration, click the *Create button*.

![Creating 3scale project](../images/3scale/05%20%20-%20APIManager%20manifest%20creation.png)

At this point, the API Manager will begin provisioning. In the list of APIManagers, the one you created should now appear. Click on it.

![Creating 3scale project](../images/3scale/06%20-%20API%20Manager%20listed.png)

Now, wait until all 15 deployments show the Running status (blue circle). Once this is done, the environment will be ready to access the admin panel.

![Creating 3scale project](../images/3scale/07%20-%20API%20Manager%20ready.png)

After installing Red Hat 3scale, a default APIManager custom resource is created, which provisions all the components required for a functional API management environment. This includes several deployments such as the API gateway (apicast), system components (backend, system, and zync), a built-in PostgreSQL database for internal data storage, and a Redis instance for caching. It also automatically configures routes for accessing the admin portal, developer portal, and API gateway endpoints. These components work together to provide a complete, ready-to-use platform for managing APIs, including access control, analytics, and developer onboarding.

Let's now retrieve some important information. In the left-hand menu, go to *Workloads > Secrets*, and look for a secret named **system-seed**. It contains the credentials needed to log in to the 3scale admin portal.

Then, in the left-hand menu again, go to *Networking > Routes* and look for the route starting with *https://3scale-admin*. Open this URL in your browser, and the 3scale login page will appear. Enter the credentials collected from the secret and click the *Sign In button*.

![Creating 3scale project](../images/3scale/08%20-%203scale%20login%20page.png)

An initial product information screen will be displayed. If you don't wish to view it, click the X at the top of the screen. You will then see the 3scale Dashboard home page.

![Creating 3scale project](../images/3scale/09%20-%203scale%20admin%20portal%20home%20page.png)


With that, we conclude our Red Hat 3scale installation tutorial. We hope it was helpful in guiding you through the first steps with the platform. Now that your environment is ready, you can start exploring 3scale’s features to manage your APIs in a secure, efficient, and scalable way. Happy testing, and see you next time!

## <h2 style="color: #e5b449;">References</h2>

Here are the references and other important links to complement the content presented:

* [Red Hat 3scale API Management - Oficial Doc](https://docs.redhat.com/en/documentation/red_hat_3scale_api_management)
* [Red Hat 3scale API Management - Product Page](https://www.redhat.com/en/technologies/jboss-middleware/3scale)
* [Red Hat 3scale API Management - Red Hat Developer Portal](https://developers.redhat.com/products/3scale/overview)
* [What is API Management? - Medium Article](https://medium.com/geekculture/what-is-api-management-4cbd6e5ffee4)
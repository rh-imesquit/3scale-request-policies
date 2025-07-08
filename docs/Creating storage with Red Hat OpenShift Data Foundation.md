# Creating storage with Red Hat OpenShift Data Foundation

In this tutorial, you will learn step by step how to provision a CephFS storage backend and expose it as a ReadWriteOnce (RWO) StorageClass on a Red Hat OpenShift cluster using the OpenShift Data Foundation (ODF) operator. We’ll cover everything from creating the infrastructure nodes and configuring the operator to defining and activating ocs-storagecluster-cephfs as the default StorageClass, ensuring high availability, replication, and Ceph’s native snapshot policies. By the end, you’ll have a fully functional test or lab environment capable of supporting dynamically provisioned volumes with optimized performance and reliable access for your applications on OpenShift.

### <h2 style="color: #e5b449;">What is</h2>

**OpenShift Data Foundation (ODF)** is Red Hat’s integrated storage solution for OpenShift that leverages Ceph to provide scalable, software-defined block, file, and object storage natively within the cluster. It simplifies data management by automating provisioning, replication, and lifecycle operations, delivering features like dynamic volume provisioning, high availability, and native snapshot and backup capabilities, all orchestrated through Kubernetes operators.

### <h2 style="color: #e5b449;">Versions Used in This Tutorial</h2>

| Component                                   | Version |
|---------------------------------------------|---------|
| Red Hat OpenShift Container Platform        | 4.18    |
| Red Hat OpenShift Data Foundation           | 4.18.6  |


### <h2 style="color: #e5b449;">Creating infra nodes</h2>

First, you need to create the infra nodes in our OpenShift cluster.

In the left-hand menu, navigate to **Compute > MachineSets**.

![Access MachineSets page](../images/ODF/01%20-%20Access%20MachineSets%20page.png)

You will see a MachineSet created by default with 0 machines. *Click on it*.

![Access MachineSets page](../images/ODF/02%20-%20Access%20MachineSets%20page.png)

On the next page, find the **Desired count field**, which will be set to 0 machines, and *change it to 3*. Then *click Save*.

![Editing machine count](../images/ODF/03%20-%20Editing%20machine%20count.png)

This action will create three additional machines, provisioned as nodes in the OpenShift cluster. It may take some time for them to become fully ready. *Click on the Machines tab* to view the list of machines associated with the MachineSet.

![Machines provisioned as nodes](../images/ODF/04%20-%20Machines%20provisioned%20as%20nodes.png)

When you view the YAML for these new nodes, you’ll notice they come by default with only the worker role. Let’s add the infra role as well. This can be done by *adding the label node-role.kubernetes.io/infra: ''*.

![Including infra label on node](../images/ODF/05%20-%20Including%20infra%20label%20on%20node.png)

After making that change, go to **Compute > Nodes** in the left-hand menu and verify that each node is now listed with both the infra and worker roles.

![Nodes with new roles](../images/ODF/06%20-%20Nodes%20with%20new%20roles.png)

We’ve completed the infra nodes creation part in the OpenShift cluster. Proceed to the next step.

### <h2 style="color: #e5b449;">ODF Installation</h2>

In the left-hand menu, go to **Operators > OperatorHub**.

In the search field, type “ODF” and select **OpenShift Data Foundation provided by Red Hat**. *Click Install*.

![Install ODF Operator](../images/ODF/07%20-%20Install%20ODF%20Operator.png)

In the installation form, keep all default options and *click Install*. Wait until the operator is installed.

![Install ODF Operator](../images/ODF/08%20-%20Install%20ODF%20Operator.png)

You may see a message marked as **Required**:

> *OpenShift Data Foundation provides a common control plane for storage solutions on OpenShift Container Platform.”*

Don’t worry—we will create the StorageSystem next.

![Install ODF Operator](../images/ODF/09%20-%20Install%20ODF%20Operator.png)

*Refresh the page (press F5)* so that the ODF plugin appears under Storage. Then go to **Storage > Data Foundation**, open the Storage Systems tab, and *click Create StorageSystem*.

![Data Foundation dashboard](../images/ODF/10%20-%20Data%20Foundation%20dashboard.png)

A form will open for creating the storage system, featuring a four-step wizard.

* Step 1 – Backing storage: keep the default values and *click Next*.

* Step 2 – Capacity and nodes: set Requested capacity to **0.5 TiB** and *select the nodes with the infra role*. Then, *click Next*.

* Step 3 – Security and network: keep the default values and *click Next*.

* Step 4 – Review and create: *click Create StorageSystem*.

![Create storage system](../images/ODF/11%20-%20Create%20storage%20system.png)

The new StorageSystem will appear in the list but may take a few minutes to become fully ready.

![Create storage system](../images/ODF/12%20-%20Create%20storage%20system.png)

Once the StorageSystem is created, you will see in the Status panels Storage Cluster and Data Resiliency with status Healthy, and in the Raw capacity panel the utilization metrics.

![Storage system details](../images/ODF/13%20-%20Storage%20system%20details.png)

### <h2 style="color: #e5b449;">Changing the Default StorageClass</h2>

Finally, we need to make one last configuration: change the default storage class type.

In the left-hand menu, go to **Storage > StorageClasses**.

In our lab, since our OpenShift cluster is provisioned on AWS, the default storage is gp3-csi; however, depending on the provider, these options may differ.

![Editing default storage class](../images/ODF/14%20-%20Editing%20default%20storage%20class.png)

Locate the default StorageClass and edit its YAML by *changing the annotation storageclass.kubernetes.io/is-default-class: "true" to storageclass.kubernetes.io/is-default-class: "false"*.

Next, edit the YAML of ocs-storagecluster-cephfs, adding or adjusting the annotation storageclass.kubernetes.io/is-default-class: "true". Save your changes.

![Editing default storage class](../images/ODF/15%20-%20Editing%20default%20storage%20class.png)

At the end, ocs-storagecluster-cephfs will be marked as the cluster’s default StorageClass.

![Editing default storage class](../images/ODF/16%20-%20Editing%20default%20storage%20class.png)

By making ocs-storagecluster-cephfs the default StorageClass, all new PersistentVolumeClaims created without specifying a storageClassName will be automatically provisioned by ODF’s CephFS, gaining support for ReadWriteMany access, replication, and high availability instead of relying on EBS gp3 volumes (which only offer ReadWriteOnce); this alters performance (latency and throughput) and inherits Ceph’s reclaim policies and snapshot capabilities.

Great job! We have completed our lab.

### <h2 style="color: #e5b449;">Conclusion</h2>


At the end of this tutorial, you will have established a CephFS storage environment fully integrated with your OpenShift cluster via the OpenShift Data Foundation operator. From configuring the infrastructure nodes and installing ODF to creating and activating ocs-storagecluster-cephfs as the default StorageClass, each step ensures that your applications benefit from dynamically provisioned volumes with replication, high availability, and Ceph’s native snapshot support.

With this foundation in place, your lab or test environment will be ready to validate workloads requiring ReadWriteOnce or ReadWriteMany access, evaluate IOPS and throughput performance, and explore advanced data management features in OpenShift. Feel free to extend this setup by tuning fault-tolerance policies, scaling nodes, and integrating with CI/CD pipelines, thereby enhancing the resilience and flexibility of your production solutions.

### <h2 style="color: #e5b449;">References</h2>

Here are the references and other important links to complement the content presented:

* [Red Hat OpenShift - Managing compute machines with the Machine API](https://docs.redhat.com/en/documentation/openshift_container_platform/4.18/html/machine_management/managing-compute-machines-with-the-machine-api)
* [Red Hat OpenShift - Working with nodes](https://docs.redhat.com/en/documentation/openshift_container_platform/4.18/html/nodes/working-with-nodes#nw-label-nodes)
* [Red Hat OpenShift - Deploy OpenShift Data Foundation using Red Hat Ceph storage](https://docs.redhat.com/en/documentation/red_hat_openshift_data_foundation/4.18/html/deploying_openshift_data_foundation_in_external_mode/deploy-openshift-data-foundation-using-red-hat-ceph-storage)
* [Red Hat OpenShift - Configuring persistent storage](https://docs.redhat.com/en/documentation/openshift_container_platform/4.18/html/storage/configuring-persistent-storage)
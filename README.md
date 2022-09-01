# CS 420 UGrad Team 15

##### Team Members

Adam Jusino
Nidheesh Kumar Kadem
Mallory Merkel
Laura Thompson

---

# Drone App

Drone App manages the various possible items and containers on a farm and controls a drone which can visit said items and containers.

### GitLab

[https://gitlab.cs.uab.edu/CS420_Group15/design-and-implementation-pt2](https://gitlab.cs.uab.edu/CS420_Group15/design-and-implementation-pt2)

### Starting the App

After you have cloned this repository, simply launch src/application/Main.java from inside of your Eclipse IDE

### Running the App

- You may **_add_** new items or containers within this root node by **selecting** this node and then choosing `New Item` or `New Container`
- You may **_edit_** or **_delete_** items or containers within this root node by **selecting** this node and then choosing `Edit` or `Delete`
- You must **select** a container node to perform actions such as **_add_**, **_edit_** or **_delete_**
- With the default value in the ChoiceBox `Scan Farm`, you may simply `Launch Drone` to scan the farm
- Alternatively, you may **_visit_** an item or container by first **selecting** it from the tree-view, choosing `Visit Selection` in the ChoiceBox, and then `Launch Drone`
- The `purchasePrice` is where you can see the total price of the container AND the item in it.
- The `marketValue` is where you can check the price of all the items inside the container and NOT the container itself.
- `Launch Drone` launches the actual drone to the visit selection selected item or container. It can also be used to scan farm where it would go around the field.
- `Launch Simulation` launches the animation of the drone to the visit selection item or container. It can also be used to scan farm where it would go around the field.

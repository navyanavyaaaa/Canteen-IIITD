This is a command-line interface (CLI) food ordering system designed specifically for a college canteen. The system caters to two primary user roles: Admin and Customer, providing distinct functionalities for each. Admins can manage menu items, process orders, generate sales reports, and handle refunds, ensuring efficient operations. Customers can browse the menu, place and track orders, maintain order history, and provide reviews. The system prioritizes VIP orders, incorporates special requests, and maintains real-time order tracking. Developed using Java, this project leverages collections like ArrayList, TreeMap, and PriorityQueue to manage data efficiently while adhering to object-oriented programming principles.
assumptions:
-order status can be viewed by customers for only that orders which is being currently processed by the admin(i.e the order which the admin is handling currently)
-customer can not cancel an order which has been denied 
-if the customer modifies the quanitity of an item in the cart to be 0 or less than zero then it is removed from the cart
- if customer doesnot have a special request its considered null
- keyword(in search functionality ) - if the keyword is contained in the item name that item will be displayed 
- if admin processes the order then its special request is handled 
- if the order is denied (one or more of the items in the order are not available or have been removed from the menu ) refund is processed 
- in view pending orders orders for respective customers will displayed in the order of they were placed but admin can only handle the order which is at the top when the status is set to delivered(or if it is denied) admin can move to the next order 

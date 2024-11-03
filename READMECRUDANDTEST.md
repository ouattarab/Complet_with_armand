3. Système de Gestion de Commandes pour un Magasin
   Contexte

Créer une application de gestion de commandes où l'on peut gérer les clients, les produits et les commandes.
Entités

    Customer (Client)
        id : Long
        name : String
        email : String
        orders : List<Order> (Relation One-to-Many)

    Product (Produit)
        id : Long
        name : String
        price : BigDecimal
        stockQuantity : int

    Order (Commande)
        id : Long
        orderDate : LocalDateTime
        customer : Customer (Relation Many-to-One)
        orderItems : List<OrderItem> (Relation One-to-Many)

    OrderItem (Détail de Commande)
        id : Long
        product : Product (Relation Many-to-One)
        quantity : int
        unitPrice : BigDecimal
        order : Order (Relation Many-to-One)

Opérations CRUD

    Customer : Ajouter un client, afficher tous les clients, mettre à jour les informations d'un client, supprimer un client.
    Product : Ajouter un produit, afficher tous les produits, mettre à jour les informations d'un produit, supprimer un produit.
    Order : Créer une commande, afficher les commandes d'un client, mettre à jour une commande, supprimer une commande.

Tests à Réaliser

    Tests CRUD pour chaque entité.
    Tests pour les relations (par exemple, vérifier qu'une commande contient plusieurs produits).
    Tests de règles de gestion (par exemple, vérifier que la quantité en stock diminue après une commande).


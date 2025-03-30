DROP TABLE IF EXISTS pizza_restrictions, ingredient_pizza, reviews, specialoffers;
DROP TABLE IF EXISTS pizzas;
--hibernate esegue questo schema all'inizio della sessione di avvio!!DEVI ELIMINARE LA TAB PIZZA PER ULTIMA! perche se elimina la tab pizzas prima allora le altre tabs che ahanno le foreigh keys collegate a pizza DA ERRORE SPRING!
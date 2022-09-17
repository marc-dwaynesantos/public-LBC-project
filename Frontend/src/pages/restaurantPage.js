import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RestaurantClient from "../api/restaurantClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RestaurantPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetRestaurants', 'renderRestaurant', 'onGetRestaurantById', 'generateRestaurants'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-restaurants').addEventListener('submit', this.onGetRestaurants);
        document.getElementById('get-by-restaurant-id').addEventListener('submit', this.onGetRestaurantById);
        document.getElementById('generate-restaurants').addEventListener('submit', this.generateRestaurants);
        this.client = new RestaurantClient();

        this.dataStore.addChangeListener(this.renderRestaurant)
        this.onGetRestaurants();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderRestaurant() {
        let resultArea = document.getElementById("restaurant-info");

        const restaurants = this.dataStore.get("restaurants");

        if (restaurants) {
            let restaurantHTML = "<ul>";
            for (let restaurant of restaurants) {
                restaurantHTML += `<li>
                <div><strong>Restaurant Name: ${restaurant.restaurantName}</strong></div>
                <div>Restaurant ID: ${restaurant.restaurantId}</div>
                <div>Restaurant Rating: ${restaurant.rating}</div>
                </li></br>`;
            }
            resultArea.innerHTML = restaurantHTML + "</ul>";
        } else {
            resultArea.innerHTML = "Loading...";
        }

        let resultArea2 = document.getElementById("restaurant-result-info2");

        const restaurant = this.dataStore.get("restaurant");

        if (restaurant) {
            resultArea.innerHTML = `
                <h4>Restaurant Name: ${restaurant.restaurantName}</h4>
                <div>Restaurant ID: ${restaurant.restaurantId}</div>
                <div>Restaurant Rating: ${restaurant.rating}</div>
                <div>Status: ${restaurant.status}</div>
                <div>Type of Cuisine: ${restaurant.cuisine}</div>
                <div>Location: ${restaurant.location}</div>
                <div>Menu:</div>
            `;
            let menuHTML = '<ul style="margin-top:-1px;">';
            for (let item of restaurant.menu) {
                menuHTML += `<li>
                <div>${item}<div>
                </li>`;
            }
            resultArea.innerHTML += menuHTML + "</ul>"
        } else {
            resultArea2.innerHTML = "No Restaurant Selected";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetRestaurants() {
        let result = await this.client.getAllRestaurants(this.errorHandler);
        this.dataStore.set("restaurants", result);
    }

    async onGetRestaurantById(event) {
        event.preventDefault();

        let restaurantId = document.getElementById('restaurant-id').value;
        let result = await this.client.getRestaurant(restaurantId, this.errorHandler);
        this.dataStore.set("restaurant", result);
    }

    async generateRestaurants(event) {
        event.preventDefault();

        const restaurant1 = await this.client.createRestaurant('1', 'McBonalds', '5', 'OPEN', 'Indian', 'New York, NY', ['Tandoori Chicken', 'Lamb Curry', 'Chicken Tikka Masala']);
        //this.dataStore.set("restaurants", restaurant1);
        const restaurant2 = await this.client.createRestaurant('2', 'KBBQ All You Can Eat', '4', 'OPEN', 'Korean', 'Brooklyn, NY', ['Pork Belly', 'Prime Short Rib', 'Beef Short Ribs']);
        //this.dataStore.set("restaurants", restaurant2);
        const restaurant3 = await this.client.createRestaurant('3', 'Grandmas Breakfast Diner', '1', 'CLOSED', 'American', 'Queens, NY', ['Egg Benedict', 'Countryside Omelette', 'Belgian Waffle']);
        //this.dataStore.set("restaurants", restaurant3);
        const restaurant4 = await this.client.createRestaurant('4', 'Downtown Taco', '3', 'OPEN', 'Mexican', 'The Bronx, NY', ['Buffalo Chicken Taco', 'Steak Quesadilla', 'BBQ Chicken Taco']);
        //this.dataStore.set("restaurants", restaurant4);
        const restaurant5 = await this.client.createRestaurant('5','Eight China', '5', 'OPEN', 'Chinese', 'Staten Island, NY', ['Peking Roasted Duck', 'Kung Pao Chicken', 'Ma Po Tofu']);
        //this.dataStore.set("restaurants", restaurant5);
        const restaurant6 = await this.client.createRestaurant('6', 'Thai 202', '4', 'OPEN', 'Thai', 'New York, NY', ['Spicy Green Papaya Salad', 'Pad Thai', 'Tom Yum Goong']);
        //this.dataStore.set("restaurants", restaurant6);
        const restaurant7 = await this.client.createRestaurant('7', 'Michaels Trattoria', '5', 'OPEN', 'Italian', 'New York, NY', ['Penne alla Vodka', 'Linguini Mare Bello', 'Chicken Francese']);
        //this.dataStore.set("restaurants", restaurant7);
        const restaurant8 = await this.client.createRestaurant('8', 'Moonstone', '4', 'OPEN', 'Japanese', 'New York, NY', ['Spicy Tuna Roll', 'Miso Ramen', 'Kara Age-Don']);
        //this.dataStore.set("restaurants", restaurant8);
        const restaurant9 = await this.client.createRestaurant('9', 'Pho Legend', '3', 'OPEN', 'Vietnamese', 'New York, NY', ['Brisket Pho', 'BBQ Beef Banh Mi', 'Summer Roll']);
        //this.dataStore.set("restaurants", restaurant9);
        const restaurant10 = await this.client.createRestaurant('10', 'Taverna Achates', '5', 'OPEN', 'Greek', 'New York, NY', ['Grilled Octopus', 'Chicken Souvlaki', 'Seafood Pasta']);
        //this.dataStore.set("restaurants", restaurant10);

        this.onGetRestaurants();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const restaurantPage = new RestaurantPage();
    restaurantPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

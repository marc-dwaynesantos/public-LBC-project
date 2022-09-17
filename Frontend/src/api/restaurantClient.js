import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class RestaurantClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getAllRestaurants', 'getRestaurant', 'createRestaurant'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getAllRestaurants(errorCallback) {
        try {
            const response = await this.client.get(`/restaurants`);
            return response.data;
        } catch (error) {
            this.handleError("getAllRestaurants", error, errorCallback);
        }
    }

    async getRestaurant(restaurantId, errorCallback) {
        try {
            const response = await this.client.get(`/restaurants/${restaurantId}`);
            return response.data;
        } catch (error) {
            this.handleError("getRestaurant", error, errorCallback)
        }
    }

    async createRestaurant(restaurantId, restaurantName, rating, status, cuisine, location, menu, errorCallback) {
        try {
            const response = await this.client.post(`/restaurants`, {
                restaurantId: restaurantId,
                restaurantName: restaurantName,
                rating: rating,
                status: status,
                cuisine: cuisine,
                location: location,
                menu: menu
            });
            return response.data;
        } catch (error) {
            this.handleError("createRestaurant", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}

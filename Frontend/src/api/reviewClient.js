import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the ReviewService.
 *
 * Users can add, update, get and delete reviews.
 */
export default class ReviewClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'updateReview', 'deleteReview', 'findByRestaurantId', 'findAll', 'addReview'];
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

    /**
     * Get all reviews
     */
     async findAll(errorCallback) {
        try {
            const response = await this.client.get(`/reviews`);
            return response.data;
        } catch (error) {
            this.handleError("findAll", error, errorCallback);
        }
    }

    /**
     * Find reviews by Restaurant Id
     */
    async findByRestaurantId(restaurantId, errorCallback) {
        try {
            const response = await this.client.get(`/reviews/${restaurantId}`);
            return response.data;
        } catch(error) {
            this.handleError("findByRestaurantId", error, errorCallback);
        }
    }


    async addReview(restaurantId, userId, rating, review, errorCallback) {
        try {
            const response = await this.client.post(`/reviews`, {
                restaurantId: restaurantId,
                userId: userId,
                rating: rating,
                review: review
            });
            return response.data;
        } catch (error) {
            this.handleError("addReview", error, errorCallback);
        }
    }

    async updateReview(restaurantId, userId, rating, review, errorCallback) {
        try {
            const response = await this.client.put(`/reviews/${restaurantId}`, {
                restaurantId: restaurantId,
                userId: userId,
                rating: rating,
                review: review
            });
            return response.data;
        } catch (error) {
            this.handleError("updateReview", error, errorCallback);
        }
    }

    //fix something
        async deleteReview(restaurantId, errorCallback) {
            try {
                const response = await this.client.delete(`/reviews/${restaurantId}`);
                return response.data;
            } catch (error) {
                this.handleError("deleteReview", error, errorCallback);
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

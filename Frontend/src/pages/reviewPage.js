import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ReviewClient from "../api/reviewClient";

/**
 * Logic needed for the view playlist page of the website.
 */

 /** Created by Marc Santos **/
class ReviewPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetReviews', 'onGetReviewById', 'onCreate', 'onUpdate', 'onDelete', 'renderReview'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-reviews').addEventListener('submit', this.onGetReviews);
        document.getElementById('get-reviews-by-restaurantId').addEventListener('submit', this.onGetReviewById);
        document.getElementById('create-reviews').addEventListener('submit', this.onCreate);
        document.getElementById('update-reviews').addEventListener('submit', this.onUpdate);
        document.getElementById('delete-reviews').addEventListener('submit', this.onDelete);

        this.client = new ReviewClient();

        this.dataStore.addChangeListener(this.renderReview)
//        this.dataStore.addChangeListener(this.renderSingleReview)
        this.onGetReviews();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderReview() {
        let resultArea = document.getElementById("result-info");

        const reviews = this.dataStore.get("reviews");

        if (reviews) {
            let reviewHTML = "<ul>";
            for (let review of reviews) {
                if (!review) {
                    continue;
                }

                reviewHTML += `<li>
                <h4>Restaurant Id: ${review.restaurantId}</h4>
                <div>User Id: ${review.userId}</div>
                <div>Rating: ${review.rating}</div>
                <div>Review: ${review.review}</div>
                </li>`;
            }
            resultArea.innerHTML = reviewHTML + "</ul>";
        } else {
            resultArea.innerHTML = "No Reviews";
        }

        let resultArea2 = document.getElementById("restaurant-result-info");

        const review = this.dataStore.get("review");

        if (review) {
            resultArea.innerHTML = `
                <div>Restaurant ID: ${review.restaurantId}</div>
                <div>By User ID: ${review.userId}</div>
                <div>Rating for Restaurant: ${review.rating}</div>
                <div>Review Written: ${review.review}</div>
            `;
        } else {
            if (resultArea2)
                resultArea2.innerHTML = "No Reviews";
        }
    }


    // Event Handlers --------------------------------------------------------------------------------------------------

        async onGetReviews() {
            let result = await this.client.findAll(this.errorHandler);
            this.dataStore.set("reviews", result);
        }

        async onGetReviewById(event) {
            event.preventDefault();

            let restaurantId = document.getElementById('get-review-restaurantId').value;
            let result = await this.client.findByRestaurantId(restaurantId, this.errorHandler);
            this.dataStore.set("review", result);
        }

        async onCreate(event) {
            // Prevent the page from refreshing on form submit
            event.preventDefault();

            let restaurantId = document.getElementById("create-new-review-restaurantId").value;
            let userId = document.getElementById("create-new-review-userId").value;
            let rating = document.getElementById("create-new-review-rating").value;
            let review = document.getElementById("create-new-review-review").value;

            const createdReview = await this.client.addReview(restaurantId, userId, rating, review, this.errorHandler);

            this.dataStore.set("reviews", [createdReview]);

            if (createdReview) {
                this.showMessage("Created a review!")
            } else {
                this.errorHandler("Review was not created. Try Again.");
            }
            this.onGetReviews();
        }

        async onUpdate(event) {
            event.preventDefault();

            let restaurantId = document.getElementById("update-review-restaurantId").value;
            let userId = document.getElementById("update-review-userId").value;
            let rating = document.getElementById("update-review-rating").value;
            let review = document.getElementById("update-review-review").value;

            const createdReview = await this.client.updateReview(restaurantId, userId, rating, review, this.errorHandler);

            this.dataStore.set("reviews", [createdReview]);

            if (createdReview) {
                this.showMessage("Review Updated!")
            } else {
                this.errorHandler("Review was not updated. Try Again.");
            }
            // Re-displays all Reviews
            this.onGetReviews();
        }

        async onDelete(event) {
            event.preventDefault();

            let restaurantId = document.getElementById("delete-review-restaurantId").value;
//            let userId = document.getElementById("delete-review-userId").value;

            const reviewToDelete = await this.client.deleteReview(restaurantId, this.errorHandler);

            this.dataStore.set("reviews", [reviewToDelete]);

            if (reviewToDelete) {
                this.showMessage("The Review has been deleted!")
            } else {
                this.errorHandler("Could not find Review. Try Again.");
            }
            // Re-displays all Reviews
            this.onGetReviews();
        }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const reviewPage = new ReviewPage();
    reviewPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

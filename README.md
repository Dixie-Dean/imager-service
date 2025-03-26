# Imager Service

The Imager Service is a web service that allows users to upload, retrieve, edit, and delete images and associated posts.

## Installation

Clone the repository:
```bash
git clone https://github.com/your-username/imager-service.git
```
Navigate to the project directory:
```bash
cd imager-service
```
Build the project using Maven:
```bash
mvn clean install
```
Run the application:
```bash
java -jar target/imager-service.jar
```

## Usage/Examples

The Imager Service provides the following API endpoints:

### Upload Imager Post
**Endpoint:** `POST /imager/upload`
**Request Body:**
- `data`: JSON string containing the post data
- `image`: Multipart file containing the image
**Response:** String message indicating the successful upload

### Get Imager Post
**Endpoint:** `GET /imager/post`
**Request Parameters:**
- `id`: The ID of the post to retrieve
**Response:** ImagerPostDTO object representing the post

### Get Imager Posts by Email
**Endpoint:** `GET /imager/posts`
**Request Parameters:**
- `email`: The email of the user to retrieve posts for
**Response:** List of ImagerPostDTO objects representing the posts

### Edit Imager Post
**Endpoint:** `PATCH /imager/edit`
**Request Body:**
- `id`: The ID of the post to edit
- `data`: (optional) JSON string containing the updated post data
- `image`: (optional) Multipart file containing the updated image
**Response:** ImagerPostDTO object representing the updated post

### Delete Imager Post
**Endpoint:** `DELETE /imager/delete`
**Request Parameters:**
- `id`: The ID of the post to delete
**Response:** String message indicating the successful deletion

## API Reference

The Imager Service exposes the following API:
- `POST /imager/upload`: Uploads a new Imager post
- `GET /imager/post`: Retrieves an Imager post by ID
- `GET /imager/posts`: Retrieves Imager posts by user email
- `PATCH /imager/edit`: Edits an existing Imager post
- `DELETE /imager/delete`: Deletes an Imager post by ID

## Running Tests

To run the unit tests for the Imager Service, execute the following command:

```bash
mvn test
```
This will run the ImagerServiceApplicationTests and ImagerPostServiceTest classes.

## Support

For any issues or inquiries, please reach out to the repository owner or create an issue in the GitHub repository.

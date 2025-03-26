# Imager Service

## Installation

1. Clone the repository:
```
git clone https://github.com/your-username/imager-service.git
```

2. Navigate to the project directory:
```
cd imager-service
```

3. Build the project using Maven:
```
mvn clean install
```

4. Run the application:
```
java -jar target/imager-service.jar
```

## Usage

The Imager Service provides the following API endpoints:

### Upload Imager Post
- **Endpoint**: `POST /imager/upload`
- **Request Body**:
  - `data`: JSON string containing the post data
  - `image`: Multipart file containing the image
- **Response**: String message indicating the successful upload

### Get Imager Post
- **Endpoint**: `GET /imager/post`
- **Request Parameters**:
  - `id`: The ID of the post to retrieve
- **Response**: `ImagerPostDTO` object representing the post

### Get Imager Posts by Email
- **Endpoint**: `GET /imager/posts`
- **Request Parameters**:
  - `email`: The email of the user to retrieve posts for
- **Response**: List of `ImagerPostDTO` objects representing the posts

### Edit Imager Post
- **Endpoint**: `PATCH /imager/edit`
- **Request Body**:
  - `id`: The ID of the post to edit
  - `data`: (optional) JSON string containing the updated post data
  - `image`: (optional) Multipart file containing the updated image
- **Response**: `ImagerPostDTO` object representing the updated post

### Delete Imager Post
- **Endpoint**: `DELETE /imager/delete`
- **Request Parameters**:
  - `id`: The ID of the post to delete
- **Response**: String message indicating the successful deletion

## API

The Imager Service exposes the following API:

- `POST /imager/upload`: Uploads a new Imager post
- `GET /imager/post`: Retrieves an Imager post by ID
- `GET /imager/posts`: Retrieves Imager posts by user email
- `PATCH /imager/edit`: Edits an existing Imager post
- `DELETE /imager/delete`: Deletes an Imager post by ID

## Contributing

To contribute to the Imager Service, follow these steps:

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Implement your changes
4. Write unit tests for your changes
5. Submit a pull request

## License

This project is licensed under the [MIT License](LICENSE).

## Testing

To run the unit tests for the Imager Service, execute the following command:

```
mvn test
```

This will run the `ImagerServiceApplicationTests` and `ImagerPostServiceTest` classes.
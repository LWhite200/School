<?php
include 'config.php';
session_start();

if (!isset($_SESSION['user'])) {
    header("Location: login.php");
    exit();
}

$user = $_SESSION['user'] ?? null;

// Function to execute SQL statements safely
function executeQuery($conn, $query, $params, $types) {
    $stmt = $conn->prepare($query);
    $stmt->bind_param($types, ...$params);
    return $stmt->execute();
}

// Handle Add or Update Animal (POST)
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST['name'];
    $breed = $_POST['breed'];
    
    if (isset($_POST['update'])) {
        $id = $_POST['id'];
        $query = "UPDATE animals SET name=?, breed=? WHERE id=?";
        $success = executeQuery($conn, $query, [$name, $breed, $id], "ssi");
    } else {
        $query = "INSERT INTO animals (name, breed) VALUES (?, ?)";
        $success = executeQuery($conn, $query, [$name, $breed], "ss");
    }
    
    $_SESSION['message'] = $success ? "Action successful!" : "Error: " . $conn->error;
    header("Location: crud.php");
    exit();
}

// Handle Delete Animal (GET - in url)
if (isset($_GET['delete'])) {
    $id = $_GET['delete'];
    $query = "DELETE FROM animals WHERE id=?";
    $success = executeQuery($conn, $query, [$id], "i");
    $_SESSION['message'] = $success ? "Animal deleted!" : "Error: " . $conn->error;
    header("Location: crud.php");
    exit();
}

// Fetch all animals
$animals = $conn->query("SELECT * FROM animals")->fetch_all(MYSQLI_ASSOC);

// Fetch the animal to update (GET)
$edit_animal = null;
if (isset($_GET['edit'])) {
    $id = $_GET['edit'];
    $result = $conn->query("SELECT * FROM animals WHERE id=$id");
    $edit_animal = $result->fetch_assoc();
}
?>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Animal CRUD App</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }
        th { background: #f4f4f4; }
        .message { color: green; }
    </style>
</head>
<body>
    <h1>Animal CRUD Application</h1>
    
    <?php if ($user): ?>
        <p>Welcome, <?php echo htmlspecialchars($user['firstname'] . ' ' . $user['lastname']); ?>!</p>
        <p><a href="logout.php">Logout</a></p>
    <?php endif; ?>
    
    <?php if (isset($_SESSION['message'])): ?>
        <p class="message"><?php echo $_SESSION['message']; unset($_SESSION['message']); ?></p>
    <?php endif; ?>
    
    <form action="" method="POST">
        <input type="hidden" name="id" value="<?php echo $edit_animal['id'] ?? ''; ?>">
        <label>Name: <input type="text" name="name" value="<?php echo $edit_animal['name'] ?? ''; ?>" required></label>
        <label>Breed: <input type="text" name="breed" value="<?php echo $edit_animal['breed'] ?? ''; ?>" required></label>
        <button type="submit" name="<?php echo isset($edit_animal) ? 'update' : 'add'; ?>">
            <?php echo isset($edit_animal) ? 'Update Animal' : 'Add Animal'; ?>
        </button>
    </form>
    
    <h2>Animal List</h2>
    <table>
        <tr><th>ID</th><th>Name</th><th>Breed</th><th>Action</th></tr>
        <?php foreach ($animals as $animal): ?>
            <tr>
                <td><?php echo $animal['id']; ?></td>
                <td><?php echo $animal['name']; ?></td>
                <td><?php echo $animal['breed']; ?></td>
                <td>
                    <a href="?edit=<?php echo $animal['id']; ?>">Edit</a> |
                    <a href="?delete=<?php echo $animal['id']; ?>" onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        <?php endforeach; ?>
    </table>
</body>
</html>

<?php $conn->close(); ?>

<?php
include 'config.inc.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $first = $_POST['first'];
    $last = $_POST['last'];
    $city = $_POST['city'];
    $country = $_POST['country'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Hash the password using bcrypt password_hash
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT, ['cost' => 12]);

    try {

        $pdo = new PDO(DBCONNSTRING, DBUSER, DBPASS);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Insert the the hashed password
        $stmt = $pdo->prepare("INSERT INTO users (firstname, lastname, city, country, email, password) 
                               VALUES (:first, :last, :city, :country, :email, :password)");

        $stmt->execute([
            ':first' => $first,
            ':last' => $last,
            ':city' => $city,
            ':country' => $country,
            ':email' => $email,
            ':password' => $hashedPassword
        ]);

        // After successful
        header("Location: login-form-bcrypt.php");
        exit();

    } catch (PDOException $e) {
        $error_message = "Error: " . $e->getMessage();
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Chapter 16</title>
    <meta charset="utf-8">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css">
    <link href="css/tailwind.css" rel="stylesheet">
</head>
<body class="bg-gray-700">

<main class="m-4 flex items-center justify-center h-screen">
    <div class="flex flex-col max-w-md px-4 py-8 bg-white rounded-lg shadow dark:bg-gray-800 sm:px-6 md:px-8 lg:px-10">
        <div class="self-center mb-2 text-xl font-light text-gray-800 sm:text-2xl dark:text-white">
            Register (bcrypt)
        </div>
        <span class="justify-center text-sm text-center text-gray-500 flex-items-center dark:text-gray-400">
            Already have an account? 
            <a href="login-form-bcrypt.php" class="text-sm text-blue-500 underline hover:text-blue-700">
                Sign in
            </a>
        </span>
        <div class="p-6 mt-2">
            <form action="" method="post">
                <div class="flex gap-4 mb-2">
                    <div class="relative">
                        <input type="text" name="first" placeholder="First name" id="create-first-name" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                    <div class="relative">
                        <input type="text" name="last" placeholder="Last name" id="create-last-name" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                </div>
                <div class="flex flex-col mb-2">
                    <div class="relative">
                        <input type="text" name="city" placeholder="City" id="create-city" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                </div>
                <div class="flex flex-col mb-2">
                    <div class="relative">
                        <input type="text" name="country" placeholder="Country" id="create-country" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                </div>
                <div class="flex flex-col mb-2">
                    <div class="relative">
                        <input type="email" name="email" placeholder="Email" id="create-account-email" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                </div>
                <div class="flex flex-col mb-2">
                    <div class="relative">
                        <input type="password" name="password" placeholder="Password" id="create-account-pass" class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" required />
                    </div>
                </div>
                <div class="flex w-full my-4">
                    <button type="submit" class="py-2 px-4 bg-purple-600 hover:bg-purple-700 focus:ring-purple-500 focus:ring-offset-purple-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg">
                        Register
                    </button>
                </div>
                <div class="flex items-center justify-center mt-2">
                    <a href="home-page.php" class="inline-flex items-center text-xs font-thin text-center text-gray-500 hover:text-gray-700 dark:text-gray-100 dark:hover:text-white">
                        <span class="ml-2">
                            Don&#x27;t want to register? Return to home.
                        </span>
                    </a>
                </div>
            </form>
        </div>
    </div>
</main>

</body>
</html>

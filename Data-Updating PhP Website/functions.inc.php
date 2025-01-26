<?php

// [[[Questiion 4]]]
# Will echo a formatted hyperlink
function generateLink($url, $label, $class) {
    # string "a href\"{$url}\" class=\"{$class}\">{$label}</a>"
    # must use htmlspecialchar   to   turn   make use 
    echo '<a href="' . htmlspecialchars($url) . '" class="' . htmlspecialchars($class) . '">' . htmlspecialchars($label) . '</a>';
}


// [[[Question 7]]]
// One through 5 inclusive 
function randomStar($ran) {
    # I am not a fan of the $, just leave blank like python.
    for ($i = 0; $i < 5; $i++) {
        $starClass = ($i < $ran) ? 'star-gold.svg' : 'star-white.svg';
        echo "<img src='images/{$starClass}' width='16' />";
    }

}


# [[[Question 6, 8, 9]]]
# Shoving all the html and css into a function that generates it from data, nice
# Used clearly use Chatgbt to guide me as I am very unfamiliar with PHP and this stuff.
function outputPostRow($post) {

    echo '<div class="row">';
    
    // [[[Image]]]
    $postId = $post['postId'];
    $thumb = $post['thumb'];
    $title = $post['title'];

    echo '<div class="col-md-4">';
    echo '<a href="' . htmlspecialchars("post.php?id=" . $postId) . '" class="img-link">';
    echo '<img src="' . htmlspecialchars("images/" . $thumb) . '" alt="' . htmlspecialchars($title) . '" class="img-responsive" />';
    echo '</a>';
    echo '</div>';


    // [[[div 8]]]
    echo '<div class="col-md-8">';
    echo "<h2>{$title}</h2>";
    echo '<div class="details">';
    
    // [[[UserName]]]
    $userId = $post['userId'];
    $userName = $post['userName'];
    echo 'Posted by ';
    generateLink("user.php?id={$userId}", $userName, "");
    
    // [[[Date Posted]]]
    $date = $post['date'];
    echo "<span class='pull-right'>{$date}</span>";
    
    // [[[Star Rating]]]
    $reviewsRating = $post['reviewsRating'];
    $reviewsNum = $post['reviewsNum'];
    echo '<p class="ratings">';
    echo randomStar(rand(0, 6));   # [[[question 7, 8]]]
    echo " {$reviewsNum} Reviews</p>";
    echo '</div>';
    
    // [[[Review Text]]]
    $excerpt = $post['excerpt'];
    echo "<p class='excerpt'>{$excerpt}</p>";

    // [[[Read More]]]
    echo "<p class='pull-right'>";
    generateLink("post.php?id={$postId}", "Read more", "btn btn-primary btn-sm");
    echo '</p>';

    // [[[closing]]]
    echo '</div>'; 
    echo '</div>';
    echo '<hr/>';
}




?>

<!-- Page Content -->
<aside class="col-md-2">
    <div class="panel panel-info">
        <div class="panel-heading">Continents</div>
        <ul class="list-group">

            <!-- New loop method [Question 4]-->
            <?php foreach ($continents as $continent): ?>
                <li class="list-group-item"><a href="#"><?php echo $continent; ?></a></li>
            <?php endforeach; ?>

        </ul>
    </div>
    <!-- end continents panel -->


    <!-- [Question 5] -->
    <div class="panel panel-info">
        <div class="panel-heading">Popular</div>
        <ul class="list-group">

            <!-- iso == abriviation -->
             <!-- Must use the . period to concotuinate string and  things together -->
            <?php foreach ($countries as $iso=>$country): ?>
                <?php generateLink("photos.php?iso=" . $iso, $country, "list-group-item"); ?>   
            <?php endforeach; ?>

        </ul>
    </div>
    <!-- end continents panel -->
</aside>
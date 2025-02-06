<div class="ui segment">
    <div class="ui form">
        <div class="ui tiny statistic">
          <div class="value">
            <?php echo  '$' . number_format($row['MSRP'],0); ?>
          </div>
        </div>
        <div class="four fields">
            <div class="three wide field">
                <label>Quantity</label>
                <input type="number">
            </div>                               
            <div class="four wide field">
                <label>Frame</label>
                <select id="frame" class="ui search dropdown">
                    <option>
                      Sample
                    </option>
                </select>
            </div>  
            <div class="four wide field">
                <label>Glass</label>
                <select id="glass" class="ui search dropdown">
                  <option>
                      Sample
                    </option>
                </select>
            </div>  
            <div class="four wide field">
                <label>Matt</label>
                <select id="matt" class="ui search dropdown">
                    <option>
                      Sample
                    </option>
                </select>
            </div>           
        </div>                     
    </div>

    <div class="ui divider"></div>

    <a class="ui labeled icon orange button" href="">
      <i class="add to cart icon"></i>
      Add to Cart
    </a>

    <!-- Step 2, because it has to be in another file than the one the instructions specified -->
    <a class="ui icon button" href="addToFavorites.php?PaintingID=<?php echo $row['PaintingID']; ?>&ImageFileName=<?php echo $row['ImageFileName']; ?>&Title=<?php echo urlencode(utf8_encode($row['Title'])); ?>">
      <i class="heart icon"></i>
      Add to Favorites
    </a>       
</div>  
<template>
    <div id="GameList">
        <div class="card w-50">
            <div class="card-header" style="background-color: #FFB703; border-radius:25px; height: 60px"></div>
            <div class="card-body">
                <div id="dummy_data">
                    <div id="name">MANGA FTW</div>
                    <div id="type">Normal</div>
                    <div id="Length">1 Week</div>
                    <button id="join">Join</button>
                </div>
                <div id="dummy_data">
                    <div id="name">MANGA FTW</div>
                    <div id="type">Normal</div>
                    <div id="Length">1 Week</div>
                    <button id="join">Join</button>
                </div>
            </div>
            <div class="card-footer" style="background-color: #FFB703; border-radius:25px; height: 60px">
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "GameList",
}
</script>

<style>
div#GameList>div {
    background-color: #8ECAE6;
    border-radius: 25px;
    margin: 0 auto;
    border: none;
}

div#dummy_data {
    display: flex;
    background-color: #023047;
    border-radius: 15px;
    height: 60px;
    color: white;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    margin: 10px;
}

button#join {
    background-color: #FB8500;
    border-radius: 15px;
    border: none;
    width: 100px;
    height: 50px;
}




/* 
div#GameList>div>div:first-child :last-child {
    background-color: #FB8500;
    border-radius: 25px;
} */
</style>
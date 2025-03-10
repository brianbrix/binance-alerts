const all_api="/api/coins/filter";
document.addEventListener("DOMContentLoaded", function () {
    refreshData(all_api);
});

function refresh() {
    refreshData(all_api);
    setTimeout(refresh, 60000);
}

refresh()
// Add a Coin Pair
document.getElementById("addCoinForm").addEventListener("submit", function (event) {
    event.preventDefault();
    let coin1 = document.getElementById("coin1").value.toUpperCase();
    let coin2 = document.getElementById("coin2").value.toUpperCase();

    fetch(`/api/coins/add/${coin1}/${coin2}`, { method: "POST" })
        .then(response => response.text())
        .then(message => {
            alert(message);
            refreshData(all_api);
        })
        .catch(error => console.error("Error:", error));
});
// Delete tracked Coin pairs
function deleteCoin(coin1, coin2) {
    if (confirm(`Are you sure you want to delete ${coin1}/${coin2}?`)) {
        fetch(`/api/coins/remove/${coin1}/${coin2}`, { method: "DELETE" })
            .then(response => response.text())
            .then(message => {
                alert(message);
                refreshData(all_api);
            })
            .catch(error => console.error("Error:", error));
    }
}


// Refresh Tracked Coin Pairs
function refreshData(api, all=true) {
    if (all)
    {
        api+="/a";
    }
    fetch(api)
        .then(response => response.json())
        .then(data => {
            let tableBody = document.getElementById("coinTable");
            tableBody.innerHTML = ""; // Clear table before updating

            data.forEach(CoinPair => {
                let row = `<tr>
                    <td>${CoinPair.coin1}</td>
                    <td>${CoinPair.coin2}</td>
                    <td>${CoinPair.lowPrice ? CoinPair.lowPrice : 'N/A'}</td>
                    <td>${CoinPair.lastPrice ? CoinPair.lastPrice : 'N/A'}</td>
                    <td>${CoinPair.highPrice ? CoinPair.highPrice : 'N/A'}</td>
                    <td>${CoinPair.lastPrice >= CoinPair.highPrice ? '<span class="text-danger">🚨 New High!</span>' : '<span class="text-success">✅ Normal</span>'}</td>
                      <td>
            <button class="btn btn-danger btn-delete" onclick="deleteCoin('${CoinPair.coin1}', '${CoinPair.coin2}')">🗑Delete</button>
        </td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching data:", error));
}
//Filter data by Coin
function filterData() {
    let coinFilter = document.getElementById("coinFilter").value.toUpperCase();
    coinFilter.length<1?coinFilter="a":coinFilter;
    let filteredApi = `${all_api}/${coinFilter}`;
    refreshData(filteredApi, false);
}

sleep 1
for i in 1 2 3 4 5; do
    curl --data "$(cat src/hermes-part$i.json)" localhost:8000/load-notifications
done

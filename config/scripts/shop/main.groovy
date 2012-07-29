
print "shop plugin";

void playerInteract() {
	event.getPlayer().sendMessage("Interaction with " + event.getClickedBlock().getType() + " [" + event.getClickedBlock().getLocation() + "]");
}

void serverCommand() {
	event.getSender().sendMessage("Command " + event.getCommand() + " [" + event.getSender() + "]");
}

void remoteServerCommand() {
	event.getSender().sendMessage("Remote Command " + event.getCommand() + " [" + event.getSender() + "]");
}

void playerCommandPreprocess() {
	event.getPlayer().sendMessage("Command Preprocess " + event.getMessage() + " [" + event.getPlayer() + "]");
}

void playerChat() {
	event.getPlayer().sendMessage("Chat " + event.getMessage() + " [" + event.getPlayer() + "]");
}

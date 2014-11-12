# Install required dependencies for redrun to work properly
function installDependencies {
	jruby -S bundle install
}

# Error out if any issues occur
set -e
installDependencies

echo All dependencies are now installed, redrun can now be run.
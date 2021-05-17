package com.dmai


// kaniko处理docker image

class Kaniko {

    protected final def script
    private final Conf conf

    Kaniko(script, Conf conf) {
        this.script = script
        this.conf = conf
    }
    public void makeAndPushImage() {
        this.script.sh String.format('''#!/busybox/sh 
        pwd
        /kaniko/executor \
        -v info \
        -c ${WORKSPACE} \
        -f ${WORKSPACE}/Dockerfile \
        --cache=true \
        --build-arg MODEL_VERSION=%s \
        --build-arg FRONTEND_ENV=%s \
        --build-arg VUE_APP_SCENE=%s \
        --destination %s \
        --push-retry 3''',
        this.conf.modelVersion,
        this.conf.getAttr('nodeEnv'),
        this.conf.vueAppScene,
        this.conf.getAttr('buildImageAddress'))
    }
}


/*
executor的其他参数：
/workspace # /kaniko/executor -h
Usage:
  executor [flags]
  executor [command]

Available Commands:
  help        Help about any command
  version     Print the version number of kaniko

Flags:
      --build-arg multi-arg type                  This flag allows you to pass in ARG values at build time. Set it repeatedly for multiple values.
      --cache                                     Use cache when building image
      --cache-copy-layers                         Caches copy layers
      --cache-dir string                          Specify a local directory to use as a cache. (default "/cache")
      --cache-repo string                         Specify a repository to use as a cache, otherwise one will be inferred from the destination provided
      --cache-ttl duration                        Cache timeout in hours. Defaults to two weeks. (default 336h0m0s)
      --cleanup                                   Clean the filesystem at the end
  -c, --context string                            Path to the dockerfile build context. (default "/workspace/")
      --context-sub-path string                   Sub path within the given context.
      --customPlatform string                     Specify the build platform if different from the current host
  -d, --destination multi-arg type                Registry the final image should be pushed to. Set it repeatedly for multiple destinations.
      --digest-file string                        Specify a file to save the digest of the built image to.
  -f, --dockerfile string                         Path to the dockerfile to be built. (default "Dockerfile")
      --force                                     Force building outside of a container
      --git gitoptions                            Branch to clone if build context is a git repository (default branch=,single-branch=false,recurse-submodules=false)
  -h, --help                                      help for executor
      --ignore-path multi-arg type                Ignore these paths when taking a snapshot. Set it repeatedly for multiple paths.
      --image-name-tag-with-digest-file string    Specify a file to save the image name w/ image tag w/ digest of the built image to.
      --image-name-with-digest-file string        Specify a file to save the image name w/ digest of the built image to.
      --insecure                                  Push to insecure registry using plain HTTP
      --insecure-pull                             Pull from insecure registry using plain HTTP
      --insecure-registry multi-arg type          Insecure registry using plain HTTP to push and pull. Set it repeatedly for multiple registries.
      --label multi-arg type                      Set metadata for an image. Set it repeatedly for multiple labels.
      --log-format string                         Log format (text, color, json) (default "color")
      --log-timestamp                             Timestamp in log output
      --no-push                                   Do not push the image to the registry
      --oci-layout-path string                    Path to save the OCI image layout of the built image.
      --push-retry int                            Number of retries for the push operation
      --registry-certificate key-value-arg type   Use the provided certificate for TLS communication with the given registry. Expected format is 'my.registry.url=/path/to/the/server/certificate'.
      --registry-mirror multi-arg type            Registry mirror to use as pull-through cache instead of docker.io. Set it repeatedly for multiple mirrors.
      --reproducible                              Strip timestamps out of the image to make it reproducible
      --single-snapshot                           Take a single snapshot at the end of the build.
      --skip-tls-verify                           Push to insecure registry ignoring TLS verify
      --skip-tls-verify-pull                      Pull from insecure registry ignoring TLS verify
      --skip-tls-verify-registry multi-arg type   Insecure registry ignoring TLS verify to push and pull. Set it repeatedly for multiple registries.
      --skip-unused-stages                        Build only used stages if defined to true. Otherwise it builds by default all stages, even the unnecessaries ones until it reaches the target stage / end of Dockerfile
      --snapshotMode string                       Change the file attributes inspected during snapshotting (default "full")
      --tarPath string                            Path to save the image in as a tarball instead of pushing
      --target string                             Set the target build stage to build
      --use-new-run                               Use the experimental run implementation for detecting changes without requiring file system snapshots.
  -v, --verbosity string                          Log level (trace, debug, info, warn, error, fatal, panic) (default "info")
      --whitelist-var-run                         Ignore /var/run directory when taking image snapshot. Set it to false to preserve /var/run/ in destination image. (Default true). (default true)

Use "executor [command] --help" for more information about a command.
*/
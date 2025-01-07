package main

import (
	"log"
	"log/slog"
	"os"

	"github.com/aws/aws-lambda-go/lambda"
	"github.com/pagopa/git-watchdog/pkg/compliance"
	"github.com/pagopa/git-watchdog/pkg/lambdacontext"
	"github.com/pagopa/git-watchdog/pkg/localcontext"
	"github.com/pagopa/git-watchdog/pkg/watchdog"
)

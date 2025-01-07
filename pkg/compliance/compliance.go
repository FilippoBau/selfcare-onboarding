package compliance

import (
	"context"
	"errors"
	"fmt"
	"log/slog"
	"os"
	"strings"
	"sync"

	"github.com/google/go-github/v64/github"
	"github.com/pagopa/git-watchdog/pkg/gitutils"
	"github.com/pagopa/git-watchdog/pkg/parser"
)

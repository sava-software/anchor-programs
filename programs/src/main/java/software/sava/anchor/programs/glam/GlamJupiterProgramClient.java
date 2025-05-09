package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.Map;

public interface GlamJupiterProgramClient {

  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient,
                                               final JupiterAccounts jupiterAccounts) {
    return new GlamJupiterProgramClientImpl(nativeProgramAccountClient, jupiterAccounts);
  }

  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, JupiterAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  GlamVaultAccounts glamVaultAccounts();

  JupiterAccounts jupiterAccounts();

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final AccountMeta inputTokenProgram,
                                        final PublicKey outputMintKey,
                                        final AccountMeta outputTokenProgram,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    return swapChecked(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        amount,
        swapInstruction,
        wrapSOL
    );
  }

  default List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputProgramStateKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapChecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, wrapSOL
    );
  }

  default List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputProgramStateKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction) {
    return swapChecked(
        inputProgramStateKey, inputMintKey,
        outputProgramStateKey, outputMintKey,
        amount, swapInstruction, true
    );
  }

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction,
                                        final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapChecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> swapChecked(final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final long amount,
                                        final Instruction swapInstruction) {
    return swapChecked(inputMintKey, outputMintKey, amount, swapInstruction, true);
  }

  NativeProgramAccountClient nativeProgramAccountClient();

  Map<PublicKey, Instruction> createSwapTokenAccountsIdempotent(final AccountMeta inputTokenProgram,
                                                                final PublicKey inputMintKey,
                                                                final AccountMeta outputTokenProgram,
                                                                final PublicKey outputMintKey);

  List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                final PublicKey inputMintKey,
                                final AccountMeta inputTokenProgram,
                                final PublicKey outputProgramStateKey,
                                final PublicKey outputMintKey,
                                final AccountMeta outputTokenProgram,
                                final long amount,
                                final Instruction swapInstruction,
                                final boolean wrapSOL);

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                             final AccountMeta inputTokenProgram,
                                             final PublicKey outputMintKey,
                                             final AccountMeta outputTokenProgram,
                                             final Instruction swapInstruction) {
    return swapUncheckedAndNoWrap(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        swapInstruction
    );
  }

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputProgramStateKey,
                                             final PublicKey inputMintKey,
                                             final PublicKey outputProgramStateKey,
                                             final PublicKey outputMintKey,
                                             final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUncheckedAndNoWrap(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        swapInstruction
    );
  }

  default Instruction swapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                             final PublicKey outputMintKey,
                                             final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUncheckedAndNoWrap(
        null, inputMintKey, tokenProgram,
        null, outputMintKey, tokenProgram,
        swapInstruction
    );
  }

  Instruction swapUncheckedAndNoWrap(final PublicKey inputProgramStateKey,
                                     final PublicKey inputMintKey,
                                     final AccountMeta inputTokenProgram,
                                     final PublicKey outputProgramStateKey,
                                     final PublicKey outputMintKey,
                                     final AccountMeta outputTokenProgram,
                                     final Instruction swapInstruction);

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final AccountMeta inputTokenProgram,
                                          final PublicKey outputMintKey,
                                          final AccountMeta outputTokenProgram,
                                          long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    return swapUnchecked(
        null, inputMintKey, inputTokenProgram,
        null, outputMintKey, outputTokenProgram,
        amount,
        swapInstruction,
        wrapSOL
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                          final PublicKey inputMintKey,
                                          final PublicKey outputProgramStateKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, wrapSOL
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                          final PublicKey inputMintKey,
                                          final PublicKey outputProgramStateKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(
        inputProgramStateKey, inputMintKey, tokenProgram,
        outputProgramStateKey, outputMintKey, tokenProgram,
        amount, swapInstruction, true
    );
  }

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction,
                                          final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                          final PublicKey outputMintKey,
                                          final long amount,
                                          final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return swapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, true);
  }

  List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                  final PublicKey inputMintKey,
                                  final AccountMeta inputTokenProgram,
                                  final PublicKey outputProgramStateKey,
                                  final PublicKey outputMintKey,
                                  final AccountMeta outputTokenProgram,
                                  final long amount,
                                  final Instruction swapInstruction,
                                  final boolean wrapSOL);
}

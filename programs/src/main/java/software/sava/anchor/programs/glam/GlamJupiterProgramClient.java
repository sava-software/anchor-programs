package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;

import java.util.List;

public interface GlamJupiterProgramClient {


  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient,
                                               final JupiterAccounts jupiterAccounts) {
    return new GlamJupiterProgramClientImpl(nativeProgramAccountClient, jupiterAccounts);
  }

  static GlamJupiterProgramClient createClient(final GlamProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, JupiterAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  JupiterAccounts jupiterAccounts();

  List<Instruction> jupiterSwapChecked(final PublicKey inputMintKey,
                                       final AccountMeta inputTokenProgram,
                                       final PublicKey outputMintKey,
                                       final AccountMeta outputTokenProgram,
                                       final long amount,
                                       final Instruction swapInstruction,
                                       final boolean wrapSOL);

  default List<Instruction> jupiterSwapChecked(final PublicKey inputMintKey,
                                               final PublicKey outputMintKey,
                                               final long amount,
                                               final Instruction swapInstruction,
                                               final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return jupiterSwapChecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> jupiterSwapChecked(final PublicKey inputMintKey,
                                               final PublicKey outputMintKey,
                                               final long amount,
                                               final Instruction swapInstruction) {
    return jupiterSwapChecked(inputMintKey, outputMintKey, amount, swapInstruction, true);
  }

  Instruction jupiterSwapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                            final AccountMeta inputTokenProgram,
                                            final PublicKey outputMintKey,
                                            final AccountMeta outputTokenProgram,
                                            final long amount,
                                            final Instruction swapInstruction);

  default Instruction jupiterSwapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                                    final PublicKey outputMintKey,
                                                    final long amount,
                                                    final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return jupiterSwapUncheckedAndNoWrap(
        inputMintKey, tokenProgram,
        outputMintKey, tokenProgram,
        amount,
        swapInstruction
    );
  }

  List<Instruction> jupiterSwapUnchecked(final PublicKey inputMintKey,
                                         final AccountMeta inputTokenProgram,
                                         final PublicKey outputMintKey,
                                         final AccountMeta outputTokenProgram,
                                         final long amount,
                                         final Instruction swapInstruction,
                                         final boolean wrapSOL);

  default List<Instruction> jupiterSwapUnchecked(final PublicKey inputMintKey,
                                                 final PublicKey outputMintKey,
                                                 final long amount,
                                                 final Instruction swapInstruction,
                                                 final boolean wrapSOL) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return jupiterSwapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, wrapSOL);
  }

  default List<Instruction> jupiterSwapUnchecked(final PublicKey inputMintKey,
                                                 final PublicKey outputMintKey,
                                                 final long amount,
                                                 final Instruction swapInstruction) {
    final var tokenProgram = solanaAccounts().readTokenProgram();
    return jupiterSwapUnchecked(inputMintKey, tokenProgram, outputMintKey, tokenProgram, amount, swapInstruction, true);
  }
}

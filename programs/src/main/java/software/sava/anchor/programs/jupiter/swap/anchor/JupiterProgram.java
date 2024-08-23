package software.sava.anchor.programs.jupiter.swap.anchor;

import java.util.List;

import software.sava.anchor.programs.jupiter.swap.anchor.types.RoutePlanStep;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class JupiterProgram {

  public static final Discriminator ROUTE_DISCRIMINATOR = toDiscriminator(229, 23, 203, 151, 122, 227, 173, 42);

  public static Instruction route(final AccountMeta invokedJupiterProgramMeta,
                                  final PublicKey tokenProgramKey,
                                  final PublicKey userTransferAuthorityKey,
                                  final PublicKey userSourceTokenAccountKey,
                                  final PublicKey userDestinationTokenAccountKey,
                                  final PublicKey destinationTokenAccountKey,
                                  final PublicKey destinationMintKey,
                                  final PublicKey platformFeeAccountKey,
                                  final RoutePlanStep[] routePlan,
                                  final long inAmount,
                                  final long quotedOutAmount,
                                  final int slippageBps,
                                  final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey)
    );

    final byte[] _data = new byte[27 + Borsh.len(routePlan)];
    int i = writeDiscriminator(ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(150, 86, 71, 116, 167, 93, 14, 104);

  public static Instruction routeWithTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey userTransferAuthorityKey,
                                                 final PublicKey userSourceTokenAccountKey,
                                                 final PublicKey userDestinationTokenAccountKey,
                                                 final PublicKey destinationTokenAccountKey,
                                                 final PublicKey destinationMintKey,
                                                 final PublicKey platformFeeAccountKey,
                                                 final PublicKey tokenLedgerKey,
                                                 final RoutePlanStep[] routePlan,
                                                 final long quotedOutAmount,
                                                 final int slippageBps,
                                                 final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(tokenLedgerKey)
    );

    final byte[] _data = new byte[19 + Borsh.len(routePlan)];
    int i = writeDiscriminator(ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR = toDiscriminator(193, 32, 155, 51, 65, 214, 156, 129);

  public static Instruction sharedAccountsRoute(final AccountMeta invokedJupiterProgramMeta,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey programAuthorityKey,
                                                final PublicKey userTransferAuthorityKey,
                                                final PublicKey sourceTokenAccountKey,
                                                final PublicKey programSourceTokenAccountKey,
                                                final PublicKey programDestinationTokenAccountKey,
                                                final PublicKey destinationTokenAccountKey,
                                                final PublicKey sourceMintKey,
                                                final PublicKey destinationMintKey,
                                                final PublicKey platformFeeAccountKey,
                                                final PublicKey token2022ProgramKey,
                                                final int id,
                                                final RoutePlanStep[] routePlan,
                                                final long inAmount,
                                                final long quotedOutAmount,
                                                final int slippageBps,
                                                final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey)
    );

    final byte[] _data = new byte[28 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator SHARED_ACCOUNTS_ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(230, 121, 143, 80, 119, 159, 106, 170);

  public static Instruction sharedAccountsRouteWithTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                                               final PublicKey tokenProgramKey,
                                                               final PublicKey programAuthorityKey,
                                                               final PublicKey userTransferAuthorityKey,
                                                               final PublicKey sourceTokenAccountKey,
                                                               final PublicKey programSourceTokenAccountKey,
                                                               final PublicKey programDestinationTokenAccountKey,
                                                               final PublicKey destinationTokenAccountKey,
                                                               final PublicKey sourceMintKey,
                                                               final PublicKey destinationMintKey,
                                                               final PublicKey platformFeeAccountKey,
                                                               final PublicKey token2022ProgramKey,
                                                               final PublicKey tokenLedgerKey,
                                                               final int id,
                                                               final RoutePlanStep[] routePlan,
                                                               final long quotedOutAmount,
                                                               final int slippageBps,
                                                               final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey),
      createRead(tokenLedgerKey)
    );

    final byte[] _data = new byte[20 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator EXACT_OUT_ROUTE_DISCRIMINATOR = toDiscriminator(208, 51, 239, 151, 123, 43, 237, 92);

  public static Instruction exactOutRoute(final AccountMeta invokedJupiterProgramMeta,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey userTransferAuthorityKey,
                                          final PublicKey userSourceTokenAccountKey,
                                          final PublicKey userDestinationTokenAccountKey,
                                          final PublicKey destinationTokenAccountKey,
                                          final PublicKey sourceMintKey,
                                          final PublicKey destinationMintKey,
                                          final PublicKey platformFeeAccountKey,
                                          final PublicKey token2022ProgramKey,
                                          final RoutePlanStep[] routePlan,
                                          final long outAmount,
                                          final long quotedInAmount,
                                          final int slippageBps,
                                          final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey)
    );

    final byte[] _data = new byte[27 + Borsh.len(routePlan)];
    int i = writeDiscriminator(EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR = toDiscriminator(176, 209, 105, 168, 154, 125, 69, 62);

  public static Instruction sharedAccountsExactOutRoute(final AccountMeta invokedJupiterProgramMeta,
                                                        final PublicKey tokenProgramKey,
                                                        final PublicKey programAuthorityKey,
                                                        final PublicKey userTransferAuthorityKey,
                                                        final PublicKey sourceTokenAccountKey,
                                                        final PublicKey programSourceTokenAccountKey,
                                                        final PublicKey programDestinationTokenAccountKey,
                                                        final PublicKey destinationTokenAccountKey,
                                                        final PublicKey sourceMintKey,
                                                        final PublicKey destinationMintKey,
                                                        final PublicKey platformFeeAccountKey,
                                                        final PublicKey token2022ProgramKey,
                                                        final int id,
                                                        final RoutePlanStep[] routePlan,
                                                        final long outAmount,
                                                        final long quotedInAmount,
                                                        final int slippageBps,
                                                        final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createRead(programAuthorityKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(sourceTokenAccountKey),
      createWrite(programSourceTokenAccountKey),
      createWrite(programDestinationTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(platformFeeAccountKey),
      createRead(token2022ProgramKey)
    );

    final byte[] _data = new byte[28 + Borsh.len(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.write(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator SET_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(228, 85, 185, 112, 78, 79, 77, 2);

  public static Instruction setTokenLedger(final AccountMeta invokedJupiterProgramMeta, final PublicKey tokenLedgerKey, final PublicKey tokenAccountKey) {
    final var keys = List.of(
      createWrite(tokenLedgerKey),
      createRead(tokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SET_TOKEN_LEDGER_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(229, 194, 212, 172, 8, 10, 134, 147);

  public static Instruction createOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                             final PublicKey openOrdersKey,
                                             final PublicKey payerKey,
                                             final PublicKey dexProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey rentKey,
                                             final PublicKey marketKey) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(dexProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(marketKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_OPEN_ORDERS_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_TOKEN_ACCOUNT_DISCRIMINATOR = toDiscriminator(147, 241, 123, 100, 244, 132, 174, 118);

  public static Instruction createTokenAccount(final AccountMeta invokedJupiterProgramMeta,
                                               final PublicKey tokenAccountKey,
                                               final PublicKey userKey,
                                               final PublicKey mintKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey systemProgramKey,
                                               final int bump) {
    final var keys = List.of(
      createWrite(tokenAccountKey),
      createWritableSigner(userKey),
      createRead(mintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_TOKEN_ACCOUNT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(28, 226, 32, 148, 188, 136, 113, 171);

  public static Instruction createProgramOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                                    final PublicKey openOrdersKey,
                                                    final PublicKey payerKey,
                                                    final PublicKey programAuthorityKey,
                                                    final PublicKey dexProgramKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey rentKey,
                                                    final PublicKey marketKey,
                                                    final int id) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(programAuthorityKey),
      createRead(dexProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(marketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedJupiterProgramMeta,
                                  final PublicKey walletKey,
                                  final PublicKey programAuthorityKey,
                                  final PublicKey systemProgramKey,
                                  final int id) {
    final var keys = List.of(
      createWrite(walletKey),
      createWrite(programAuthorityKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator CLAIM_TOKEN_DISCRIMINATOR = toDiscriminator(116, 206, 27, 191, 166, 19, 0, 73);

  public static Instruction claimToken(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey payerKey,
                                       final PublicKey walletKey,
                                       final PublicKey programAuthorityKey,
                                       final PublicKey programTokenAccountKey,
                                       final PublicKey destinationTokenAccountKey,
                                       final PublicKey mintKey,
                                       final PublicKey associatedTokenTokenProgramKey,
                                       final PublicKey associatedTokenProgramKey,
                                       final PublicKey systemProgramKey,
                                       final int id) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(walletKey),
      createRead(programAuthorityKey),
      createWrite(programTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(mintKey),
      createRead(associatedTokenTokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_TOKEN_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public static final Discriminator CREATE_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(232, 242, 197, 253, 240, 143, 129, 52);

  public static Instruction createTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                              final PublicKey tokenLedgerKey,
                                              final PublicKey payerKey,
                                              final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(tokenLedgerKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_TOKEN_LEDGER_DISCRIMINATOR);
  }
  
  private JupiterProgram() {
  }
}
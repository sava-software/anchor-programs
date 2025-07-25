package software.sava.anchor.programs.jupiter.swap.anchor;

import java.util.List;

import software.sava.anchor.programs.jupiter.swap.anchor.types.RoutePlanStep;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class JupiterProgram {

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedJupiterProgramMeta,
                                  final SolanaAccounts solanaAccounts,
                                  final PublicKey walletKey,
                                  final PublicKey programAuthorityKey,
                                  final int id) {
    final var keys = List.of(
      createWrite(walletKey),
      createWrite(programAuthorityKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record ClaimIxData(Discriminator discriminator, int id) implements Borsh {  

    public static ClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      return new ClaimIxData(discriminator, id);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAIM_TOKEN_DISCRIMINATOR = toDiscriminator(116, 206, 27, 191, 166, 19, 0, 73);

  public static Instruction claimToken(final AccountMeta invokedJupiterProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey payerKey,
                                       final PublicKey walletKey,
                                       final PublicKey programAuthorityKey,
                                       final PublicKey programTokenAccountKey,
                                       final PublicKey destinationTokenAccountKey,
                                       final PublicKey mintKey,
                                       final PublicKey tokenProgramKey,
                                       final int id) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(walletKey),
      createRead(programAuthorityKey),
      createWrite(programTokenAccountKey),
      createWrite(destinationTokenAccountKey),
      createRead(mintKey),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_TOKEN_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record ClaimTokenIxData(Discriminator discriminator, int id) implements Borsh {  

    public static ClaimTokenIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ClaimTokenIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      return new ClaimTokenIxData(discriminator, id);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_TOKEN_DISCRIMINATOR = toDiscriminator(26, 74, 236, 151, 104, 64, 183, 249);

  public static Instruction closeToken(final AccountMeta invokedJupiterProgramMeta,
                                       final PublicKey operatorKey,
                                       final PublicKey walletKey,
                                       final PublicKey programAuthorityKey,
                                       final PublicKey programTokenAccountKey,
                                       final PublicKey mintKey,
                                       final PublicKey tokenProgramKey,
                                       final int id,
                                       final boolean burnAll) {
    final var keys = List.of(
      createReadOnlySigner(operatorKey),
      createWrite(walletKey),
      createRead(programAuthorityKey),
      createWrite(programTokenAccountKey),
      createWrite(mintKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(CLOSE_TOKEN_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    _data[i] = (byte) (burnAll ? 1 : 0);

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record CloseTokenIxData(Discriminator discriminator, int id, boolean burnAll) implements Borsh {  

    public static CloseTokenIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static CloseTokenIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      ++i;
      final var burnAll = _data[i] == 1;
      return new CloseTokenIxData(discriminator, id, burnAll);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      _data[i] = (byte) (burnAll ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(229, 194, 212, 172, 8, 10, 134, 147);

  public static Instruction createOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             final PublicKey openOrdersKey,
                                             final PublicKey payerKey,
                                             final PublicKey dexProgramKey,
                                             final PublicKey marketKey) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(dexProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(marketKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_OPEN_ORDERS_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR = toDiscriminator(28, 226, 32, 148, 188, 136, 113, 171);

  public static Instruction createProgramOpenOrders(final AccountMeta invokedJupiterProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey openOrdersKey,
                                                    final PublicKey payerKey,
                                                    final PublicKey programAuthorityKey,
                                                    final PublicKey dexProgramKey,
                                                    final PublicKey marketKey,
                                                    final int id) {
    final var keys = List.of(
      createWrite(openOrdersKey),
      createWritableSigner(payerKey),
      createRead(programAuthorityKey),
      createRead(dexProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(marketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_PROGRAM_OPEN_ORDERS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record CreateProgramOpenOrdersIxData(Discriminator discriminator, int id) implements Borsh {  

    public static CreateProgramOpenOrdersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CreateProgramOpenOrdersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      return new CreateProgramOpenOrdersIxData(discriminator, id);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(232, 242, 197, 253, 240, 143, 129, 52);

  public static Instruction createTokenLedger(final AccountMeta invokedJupiterProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey tokenLedgerKey,
                                              final PublicKey payerKey) {
    final var keys = List.of(
      createWritableSigner(tokenLedgerKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, CREATE_TOKEN_LEDGER_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_TOKEN_ACCOUNT_DISCRIMINATOR = toDiscriminator(147, 241, 123, 100, 244, 132, 174, 118);

  public static Instruction createTokenAccount(final AccountMeta invokedJupiterProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey tokenAccountKey,
                                               final PublicKey userKey,
                                               final PublicKey mintKey,
                                               final PublicKey tokenProgramKey,
                                               final int bump) {
    final var keys = List.of(
      createWrite(tokenAccountKey),
      createWritableSigner(userKey),
      createRead(mintKey),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CREATE_TOKEN_ACCOUNT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record CreateTokenAccountIxData(Discriminator discriminator, int bump) implements Borsh {  

    public static CreateTokenAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CreateTokenAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bump = _data[i] & 0xFF;
      return new CreateTokenAccountIxData(discriminator, bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
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
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
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
      createWrite(requireNonNullElse(destinationTokenAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(sourceMintKey),
      createRead(destinationMintKey),
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(requireNonNullElse(token2022ProgramKey, invokedJupiterProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[27 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record ExactOutRouteIxData(Discriminator discriminator,
                                    RoutePlanStep[] routePlan,
                                    long outAmount,
                                    long quotedInAmount,
                                    int slippageBps,
                                    int platformFeeBps) implements Borsh {  

    public static ExactOutRouteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ExactOutRouteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var outAmount = getInt64LE(_data, i);
      i += 8;
      final var quotedInAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new ExactOutRouteIxData(discriminator,
                                     routePlan,
                                     outAmount,
                                     quotedInAmount,
                                     slippageBps,
                                     platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, outAmount);
      i += 8;
      putInt64LE(_data, i, quotedInAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(routePlan)
           + 8
           + 8
           + 2
           + 1;
    }
  }

  public static final Discriminator ROUTE_DISCRIMINATOR = toDiscriminator(229, 23, 203, 151, 122, 227, 173, 42);

  // route_plan Topologically sorted trade DAG
  public static Instruction route(final AccountMeta invokedJupiterProgramMeta,
                                  final PublicKey tokenProgramKey,
                                  final PublicKey userTransferAuthorityKey,
                                  final PublicKey userSourceTokenAccountKey,
                                  final PublicKey userDestinationTokenAccountKey,
                                  final PublicKey destinationTokenAccountKey,
                                  final PublicKey destinationMintKey,
                                  final PublicKey platformFeeAccountKey,
                                  final PublicKey eventAuthorityKey,
                                  final PublicKey programKey,
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
      createWrite(requireNonNullElse(destinationTokenAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(destinationMintKey),
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[27 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(ROUTE_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record RouteIxData(Discriminator discriminator,
                            RoutePlanStep[] routePlan,
                            long inAmount,
                            long quotedOutAmount,
                            int slippageBps,
                            int platformFeeBps) implements Borsh {  

    public static RouteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RouteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var inAmount = getInt64LE(_data, i);
      i += 8;
      final var quotedOutAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new RouteIxData(discriminator,
                             routePlan,
                             inAmount,
                             quotedOutAmount,
                             slippageBps,
                             platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, inAmount);
      i += 8;
      putInt64LE(_data, i, quotedOutAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(routePlan)
           + 8
           + 8
           + 2
           + 1;
    }
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
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final RoutePlanStep[] routePlan,
                                                 final long quotedOutAmount,
                                                 final int slippageBps,
                                                 final int platformFeeBps) {
    final var keys = List.of(
      createRead(tokenProgramKey),
      createReadOnlySigner(userTransferAuthorityKey),
      createWrite(userSourceTokenAccountKey),
      createWrite(userDestinationTokenAccountKey),
      createWrite(requireNonNullElse(destinationTokenAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(destinationMintKey),
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(tokenLedgerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[19 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record RouteWithTokenLedgerIxData(Discriminator discriminator,
                                           RoutePlanStep[] routePlan,
                                           long quotedOutAmount,
                                           int slippageBps,
                                           int platformFeeBps) implements Borsh {  

    public static RouteWithTokenLedgerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RouteWithTokenLedgerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var quotedOutAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new RouteWithTokenLedgerIxData(discriminator,
                                            routePlan,
                                            quotedOutAmount,
                                            slippageBps,
                                            platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, quotedOutAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(routePlan) + 8 + 2 + 1;
    }
  }

  public static final Discriminator SET_TOKEN_LEDGER_DISCRIMINATOR = toDiscriminator(228, 85, 185, 112, 78, 79, 77, 2);

  public static Instruction setTokenLedger(final AccountMeta invokedJupiterProgramMeta, final PublicKey tokenLedgerKey, final PublicKey tokenAccountKey) {
    final var keys = List.of(
      createWrite(tokenLedgerKey),
      createRead(tokenAccountKey)
    );

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, SET_TOKEN_LEDGER_DISCRIMINATOR);
  }

  public static final Discriminator SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR = toDiscriminator(176, 209, 105, 168, 154, 125, 69, 62);

  // Route by using program owned token accounts and open orders accounts.
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
                                                        final PublicKey eventAuthorityKey,
                                                        final PublicKey programKey,
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
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(requireNonNullElse(token2022ProgramKey, invokedJupiterProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[28 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_EXACT_OUT_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, outAmount);
    i += 8;
    putInt64LE(_data, i, quotedInAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record SharedAccountsExactOutRouteIxData(Discriminator discriminator,
                                                  int id,
                                                  RoutePlanStep[] routePlan,
                                                  long outAmount,
                                                  long quotedInAmount,
                                                  int slippageBps,
                                                  int platformFeeBps) implements Borsh {  

    public static SharedAccountsExactOutRouteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SharedAccountsExactOutRouteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      ++i;
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var outAmount = getInt64LE(_data, i);
      i += 8;
      final var quotedInAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new SharedAccountsExactOutRouteIxData(discriminator,
                                                   id,
                                                   routePlan,
                                                   outAmount,
                                                   quotedInAmount,
                                                   slippageBps,
                                                   platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, outAmount);
      i += 8;
      putInt64LE(_data, i, quotedInAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1
           + Borsh.lenVector(routePlan)
           + 8
           + 8
           + 2
           + 1;
    }
  }

  public static final Discriminator SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR = toDiscriminator(193, 32, 155, 51, 65, 214, 156, 129);

  // Route by using program owned token accounts and open orders accounts.
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
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
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
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(requireNonNullElse(token2022ProgramKey, invokedJupiterProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[28 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record SharedAccountsRouteIxData(Discriminator discriminator,
                                          int id,
                                          RoutePlanStep[] routePlan,
                                          long inAmount,
                                          long quotedOutAmount,
                                          int slippageBps,
                                          int platformFeeBps) implements Borsh {  

    public static SharedAccountsRouteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SharedAccountsRouteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      ++i;
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var inAmount = getInt64LE(_data, i);
      i += 8;
      final var quotedOutAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new SharedAccountsRouteIxData(discriminator,
                                           id,
                                           routePlan,
                                           inAmount,
                                           quotedOutAmount,
                                           slippageBps,
                                           platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, inAmount);
      i += 8;
      putInt64LE(_data, i, quotedOutAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1
           + Borsh.lenVector(routePlan)
           + 8
           + 8
           + 2
           + 1;
    }
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
                                                               final PublicKey eventAuthorityKey,
                                                               final PublicKey programKey,
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
      createWrite(requireNonNullElse(platformFeeAccountKey, invokedJupiterProgramMeta.publicKey())),
      createRead(requireNonNullElse(token2022ProgramKey, invokedJupiterProgramMeta.publicKey())),
      createRead(tokenLedgerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[20 + Borsh.lenVector(routePlan)];
    int i = writeDiscriminator(SHARED_ACCOUNTS_ROUTE_WITH_TOKEN_LEDGER_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) id;
    ++i;
    i += Borsh.writeVector(routePlan, _data, i);
    putInt64LE(_data, i, quotedOutAmount);
    i += 8;
    putInt16LE(_data, i, slippageBps);
    i += 2;
    _data[i] = (byte) platformFeeBps;

    return Instruction.createInstruction(invokedJupiterProgramMeta, keys, _data);
  }

  public record SharedAccountsRouteWithTokenLedgerIxData(Discriminator discriminator,
                                                         int id,
                                                         RoutePlanStep[] routePlan,
                                                         long quotedOutAmount,
                                                         int slippageBps,
                                                         int platformFeeBps) implements Borsh {  

    public static SharedAccountsRouteWithTokenLedgerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SharedAccountsRouteWithTokenLedgerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var id = _data[i] & 0xFF;
      ++i;
      final var routePlan = Borsh.readVector(RoutePlanStep.class, RoutePlanStep::read, _data, i);
      i += Borsh.lenVector(routePlan);
      final var quotedOutAmount = getInt64LE(_data, i);
      i += 8;
      final var slippageBps = getInt16LE(_data, i);
      i += 2;
      final var platformFeeBps = _data[i] & 0xFF;
      return new SharedAccountsRouteWithTokenLedgerIxData(discriminator,
                                                          id,
                                                          routePlan,
                                                          quotedOutAmount,
                                                          slippageBps,
                                                          platformFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) id;
      ++i;
      i += Borsh.writeVector(routePlan, _data, i);
      putInt64LE(_data, i, quotedOutAmount);
      i += 8;
      putInt16LE(_data, i, slippageBps);
      i += 2;
      _data[i] = (byte) platformFeeBps;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1
           + Borsh.lenVector(routePlan)
           + 8
           + 2
           + 1;
    }
  }

  private JupiterProgram() {
  }
}

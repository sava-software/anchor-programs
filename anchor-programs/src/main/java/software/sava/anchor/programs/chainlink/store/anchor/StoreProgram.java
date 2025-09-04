package software.sava.anchor.programs.chainlink.store.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.chainlink.store.anchor.types.NewTransmission;
import software.sava.anchor.programs.chainlink.store.anchor.types.Scope;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class StoreProgram {

  public static final Discriminator CREATE_FEED_DISCRIMINATOR = toDiscriminator(173, 86, 95, 94, 13, 193, 67, 180);

  public static Instruction createFeed(final AccountMeta invokedStoreProgramMeta,
                                       final PublicKey feedKey,
                                       final PublicKey authorityKey,
                                       final String description,
                                       final int decimals,
                                       final int granularity,
                                       final int liveLength) {
    final var keys = List.of(
      createWrite(feedKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _description = description.getBytes(UTF_8);
    final byte[] _data = new byte[18 + Borsh.lenVector(_description)];
    int i = CREATE_FEED_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(_description, _data, i);
    _data[i] = (byte) decimals;
    ++i;
    _data[i] = (byte) granularity;
    ++i;
    putInt32LE(_data, i, liveLength);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record CreateFeedIxData(Discriminator discriminator,
                                 String description, byte[] _description,
                                 int decimals,
                                 int granularity,
                                 int liveLength) implements Borsh {  

    public static CreateFeedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateFeedIxData createRecord(final Discriminator discriminator,
                                                final String description,
                                                final int decimals,
                                                final int granularity,
                                                final int liveLength) {
      return new CreateFeedIxData(discriminator,
                                  description, description.getBytes(UTF_8),
                                  decimals,
                                  granularity,
                                  liveLength);
    }

    public static CreateFeedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var description = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var decimals = _data[i] & 0xFF;
      ++i;
      final var granularity = _data[i] & 0xFF;
      ++i;
      final var liveLength = getInt32LE(_data, i);
      return new CreateFeedIxData(discriminator,
                                  description, description.getBytes(UTF_8),
                                  decimals,
                                  granularity,
                                  liveLength);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_description, _data, i);
      _data[i] = (byte) decimals;
      ++i;
      _data[i] = (byte) granularity;
      ++i;
      putInt32LE(_data, i, liveLength);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_description) + 1 + 1 + 4;
    }
  }

  public static final Discriminator CLOSE_FEED_DISCRIMINATOR = toDiscriminator(153, 14, 92, 89, 19, 78, 211, 46);

  public static Instruction closeFeed(final AccountMeta invokedStoreProgramMeta,
                                      final PublicKey feedKey,
                                      final PublicKey ownerKey,
                                      final PublicKey receiverKey,
                                      final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(ownerKey),
      createWrite(receiverKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, CLOSE_FEED_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_FEED_OWNERSHIP_DISCRIMINATOR = toDiscriminator(181, 58, 251, 37, 147, 180, 70, 227);

  public static Instruction transferFeedOwnership(final AccountMeta invokedStoreProgramMeta,
                                                  final PublicKey feedKey,
                                                  final PublicKey ownerKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey proposedOwner) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(ownerKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[40];
    int i = TRANSFER_FEED_OWNERSHIP_DISCRIMINATOR.write(_data, 0);
    proposedOwner.write(_data, i);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record TransferFeedOwnershipIxData(Discriminator discriminator, PublicKey proposedOwner) implements Borsh {  

    public static TransferFeedOwnershipIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static TransferFeedOwnershipIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proposedOwner = readPubKey(_data, i);
      return new TransferFeedOwnershipIxData(discriminator, proposedOwner);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      proposedOwner.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ACCEPT_FEED_OWNERSHIP_DISCRIMINATOR = toDiscriminator(211, 161, 86, 113, 101, 175, 245, 136);

  public static Instruction acceptFeedOwnership(final AccountMeta invokedStoreProgramMeta,
                                                final PublicKey feedKey,
                                                final PublicKey proposedOwnerKey,
                                                final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(proposedOwnerKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, ACCEPT_FEED_OWNERSHIP_DISCRIMINATOR);
  }

  public static final Discriminator SET_VALIDATOR_CONFIG_DISCRIMINATOR = toDiscriminator(87, 248, 224, 193, 17, 41, 80, 250);

  public static Instruction setValidatorConfig(final AccountMeta invokedStoreProgramMeta,
                                               final PublicKey feedKey,
                                               final PublicKey ownerKey,
                                               final PublicKey authorityKey,
                                               final int flaggingThreshold) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(ownerKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[12];
    int i = SET_VALIDATOR_CONFIG_DISCRIMINATOR.write(_data, 0);
    putInt32LE(_data, i, flaggingThreshold);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record SetValidatorConfigIxData(Discriminator discriminator, int flaggingThreshold) implements Borsh {  

    public static SetValidatorConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static SetValidatorConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var flaggingThreshold = getInt32LE(_data, i);
      return new SetValidatorConfigIxData(discriminator, flaggingThreshold);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, flaggingThreshold);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_WRITER_DISCRIMINATOR = toDiscriminator(174, 36, 177, 122, 86, 142, 32, 109);

  public static Instruction setWriter(final AccountMeta invokedStoreProgramMeta,
                                      final PublicKey feedKey,
                                      final PublicKey ownerKey,
                                      final PublicKey authorityKey,
                                      final PublicKey writer) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(ownerKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[40];
    int i = SET_WRITER_DISCRIMINATOR.write(_data, 0);
    writer.write(_data, i);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record SetWriterIxData(Discriminator discriminator, PublicKey writer) implements Borsh {  

    public static SetWriterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetWriterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var writer = readPubKey(_data, i);
      return new SetWriterIxData(discriminator, writer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      writer.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LOWER_FLAG_DISCRIMINATOR = toDiscriminator(253, 79, 240, 163, 36, 124, 157, 111);

  public static Instruction lowerFlag(final AccountMeta invokedStoreProgramMeta,
                                      final PublicKey feedKey,
                                      final PublicKey ownerKey,
                                      final PublicKey authorityKey,
                                      final PublicKey accessControllerKey) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(ownerKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, LOWER_FLAG_DISCRIMINATOR);
  }

  public static final Discriminator SUBMIT_DISCRIMINATOR = toDiscriminator(88, 166, 102, 181, 162, 127, 170, 48);

  public static Instruction submit(final AccountMeta invokedStoreProgramMeta,
                                   final PublicKey feedKey,
                                   final PublicKey authorityKey,
                                   final NewTransmission round) {
    final var keys = List.of(
      createWrite(feedKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(round)];
    int i = SUBMIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(round, _data, i);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record SubmitIxData(Discriminator discriminator, NewTransmission round) implements Borsh {  

    public static SubmitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static SubmitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var round = NewTransmission.read(_data, i);
      return new SubmitIxData(discriminator, round);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(round, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedStoreProgramMeta,
                                       final PublicKey storeKey,
                                       final PublicKey ownerKey,
                                       final PublicKey loweringAccessControllerKey) {
    final var keys = List.of(
      createWrite(storeKey),
      createReadOnlySigner(ownerKey),
      createRead(loweringAccessControllerKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_STORE_OWNERSHIP_DISCRIMINATOR = toDiscriminator(186, 90, 25, 87, 17, 175, 57, 109);

  public static Instruction transferStoreOwnership(final AccountMeta invokedStoreProgramMeta,
                                                   final PublicKey storeKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey proposedOwner) {
    final var keys = List.of(
      createWrite(storeKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[40];
    int i = TRANSFER_STORE_OWNERSHIP_DISCRIMINATOR.write(_data, 0);
    proposedOwner.write(_data, i);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record TransferStoreOwnershipIxData(Discriminator discriminator, PublicKey proposedOwner) implements Borsh {  

    public static TransferStoreOwnershipIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static TransferStoreOwnershipIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proposedOwner = readPubKey(_data, i);
      return new TransferStoreOwnershipIxData(discriminator, proposedOwner);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      proposedOwner.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ACCEPT_STORE_OWNERSHIP_DISCRIMINATOR = toDiscriminator(239, 190, 50, 245, 242, 158, 249, 230);

  public static Instruction acceptStoreOwnership(final AccountMeta invokedStoreProgramMeta, final PublicKey storeKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(storeKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, ACCEPT_STORE_OWNERSHIP_DISCRIMINATOR);
  }

  public static final Discriminator SET_LOWERING_ACCESS_CONTROLLER_DISCRIMINATOR = toDiscriminator(207, 68, 147, 34, 164, 94, 189, 113);

  public static Instruction setLoweringAccessController(final AccountMeta invokedStoreProgramMeta,
                                                        final PublicKey storeKey,
                                                        final PublicKey authorityKey,
                                                        final PublicKey accessControllerKey) {
    final var keys = List.of(
      createWrite(storeKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey)
    );

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, SET_LOWERING_ACCESS_CONTROLLER_DISCRIMINATOR);
  }

  public static final Discriminator QUERY_DISCRIMINATOR = toDiscriminator(39, 251, 130, 159, 46, 136, 164, 169);

  public static Instruction query(final AccountMeta invokedStoreProgramMeta, final PublicKey feedKey, final Scope scope) {
    final var keys = List.of(
      createRead(feedKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(scope)];
    int i = QUERY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(scope, _data, i);

    return Instruction.createInstruction(invokedStoreProgramMeta, keys, _data);
  }

  public record QueryIxData(Discriminator discriminator, Scope scope) implements Borsh {  

    public static QueryIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static QueryIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var scope = Scope.read(_data, i);
      return new QueryIxData(discriminator, scope);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(scope, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(scope);
    }
  }

  private StoreProgram() {
  }
}
